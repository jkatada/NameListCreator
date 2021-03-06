package jp.namelist.modelbuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

import jp.namelist.model.AbstractModifiableModel;
import jp.namelist.model.AnnotationModel;
import jp.namelist.model.AnnotationParameterModel;
import jp.namelist.model.MethodModel;
import jp.namelist.model.MethodParameterModel;
import jp.namelist.model.PackageModel;
import jp.namelist.model.ProjectModel;
import jp.namelist.model.TypeModel;
import jp.namelist.modelbuilder.filters.BuildModelFilter;
import jp.namelist.modelbuilder.filters.DefaultBuildModelFilter;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

class BuildModelVisitor extends ASTVisitor {

	private BuildModelFilter filter;

	private ProjectModel currentProject;
	private PackageModel currentPackage;
	private Stack<TypeModel> currentType = new Stack<>();
	private Stack<MethodModel> currentMethod = new Stack<>();

	public BuildModelVisitor(String projectName) {
		this(projectName, new DefaultBuildModelFilter());
	}

	public BuildModelVisitor(String projectName, BuildModelFilter filter) {
		currentProject = new ProjectModel(projectName);
		this.filter = filter;
	}

	@Override
	public boolean visit(PackageDeclaration node) {
		Map<String, PackageModel> packages = currentProject.getPackages();
		String packageName = node.getName().getFullyQualifiedName(); 
		if (packages.containsKey(packageName)) {
			currentPackage = packages.get(packageName);
		} else {
			PackageModel newPackageModel = new PackageModel(packageName);
			newPackageModel.setJavaDoc(Objects.toString(node.getJavadoc(), ""));
			currentProject.addPackage(newPackageModel);
			currentPackage = newPackageModel;
		}
		return super.visit(node);
	}
	
	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		TypeModel typeModel = new TypeModel("[Anonymous Class in "
				+ currentType.peek().getName() + "#"
				+ currentMethod.peek().getName() + "]");

		currentPackage.addType(typeModel);
		currentType.push(typeModel);

		return super.visit(node);
	}

	@Override
	public void endVisit(AnonymousClassDeclaration node) {
		currentType.pop();
		super.endVisit(node);
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		TypeModel typeModel = new TypeModel(node.getName().getIdentifier());
		typeModel.setJavaDoc(Objects.toString(node.getJavadoc(), ""));
		analyzeModifier(node.modifiers(), typeModel);

		currentPackage.addType(typeModel);
		currentType.push(typeModel);

		return super.visit(node);
	}

	@Override
	public void endVisit(TypeDeclaration node) {
		currentType.pop();
		super.endVisit(node);
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		TypeModel typeModel = new TypeModel(node.getName().getIdentifier());
		typeModel.setJavaDoc(Objects.toString(node.getJavadoc(), ""));
		analyzeModifier(node.modifiers(), typeModel);

		currentPackage.addType(typeModel);
		currentType.push(typeModel);

		return super.visit(node);
	}

	@Override
	public void endVisit(EnumDeclaration node) {
		currentType.pop();
		super.endVisit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		MethodModel methodModel = new MethodModel(node.getName()
				.getFullyQualifiedName());
		methodModel.setJavaDoc(Objects.toString(node.getJavadoc(), ""));
		analyzeModifier(node.modifiers(), methodModel);

		// 戻り値の型の情報を取得
		Type returnType = node.getReturnType2();
		methodModel.setReturnType(Objects.toString(returnType, ""));

		// 引数の情報を取得
		@SuppressWarnings("unchecked")
		List<SingleVariableDeclaration> params = node.parameters();
		for (SingleVariableDeclaration param : params) {
			String paramName = param.getName().getIdentifier();
			String paramType = param.getType().toString();
			MethodParameterModel paramModel = new MethodParameterModel(
					paramName);
			paramModel.setType(paramType);
			paramModel.setText(param.toString());
			methodModel.addParam(paramModel);
			analyzeModifier(param.modifiers(), paramModel);
		}

		currentType.peek().addMethod(methodModel);
		currentMethod.push(methodModel);

		return super.visit(node);
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		currentMethod.pop();

		super.endVisit(node);
	};

	@Override
	public boolean visit(ReturnStatement node) {
		currentMethod.peek().addMethodReturn(Objects.toString(node.getExpression(), ""));
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodInvocation node) {
		if (filter.isTarget(node)) {
			currentMethod.peek().addMethodInvocation(
					node.toString());

			// メソッド呼び出しの引数情報を取得
			// List<Expression> args = invocation.arguments();
			// for(Expression arg : args) {
			// createNode("arg", invocationNode).setAttribute("expression",
			// arg.toString());
			// }
			// createTextNode(invocationNode).setTextContent(invocation.toString());
			//
			// currentNode = invocationNode;
		}
		return super.visit(node);
	}

	private void analyzeModifier(List modifiers, AbstractModifiableModel model) {
		for (Object modifier : modifiers) {
			// 型につけられたアノテーション情報の取得
			if (modifier instanceof Annotation) {
				Annotation annotation = (Annotation) modifier;
				AnnotationModel annotationModel = new AnnotationModel(
						annotation.getTypeName().toString());
				model.addAnnotation(annotationModel);

				if (annotation instanceof NormalAnnotation) {
					// 通常のアノテーションの場合
					NormalAnnotation na = (NormalAnnotation) annotation;
					@SuppressWarnings("unchecked")
					List<MemberValuePair> valuePairs = na.values();
					for (MemberValuePair valuePair : valuePairs) {
						String paramName = valuePair.getName().getIdentifier();
						String paramValue = valuePair.getValue().toString();
						AnnotationParameterModel aParam = new AnnotationParameterModel(paramName);
						aParam.setValue(paramValue);
						annotationModel.addParam(aParam);
					}
				} else if (annotation instanceof SingleMemberAnnotation) {
					// 暗黙のvalueのみを持つアノテーションの場合
					SingleMemberAnnotation sma = (SingleMemberAnnotation) annotation;
					String paramName = "value";
					String paramValue = sma.getValue().toString();
					AnnotationParameterModel aParam = new AnnotationParameterModel(paramName);
					aParam.setValue(paramValue);
					annotationModel.addParam(aParam);
				}

				// アノテーションのテキスト表現をtextタグに保持
				annotationModel.setText(annotation.toString());

			} else if (modifier instanceof Modifier) {
				// その他のModifier(abstract, publicなど)を記録
				model.addModifier(modifier.toString());
			} else {
				throw new IllegalStateException(
						"modifier is not Modifier instance. modifier getClass() : "
								+ modifier.getClass());
			}
		}
	}

	public ProjectModel getProject() {
		return currentProject;
	}

}
