package jp.namelist.analyzer;

import java.util.List;
import java.util.Stack;

import jp.namelist.model.AbstractModifiableModel;
import jp.namelist.model.AnnotationModel;
import jp.namelist.model.CompilationUnitModel;
import jp.namelist.model.MethodModel;
import jp.namelist.model.MethodParameterModel;
import jp.namelist.model.PackageModel;
import jp.namelist.model.ProjectModel;
import jp.namelist.model.TypeModel;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ModelBuilder {

	private ProjectModel currentProject;
	private Stack<PackageModel> currentPackage = new Stack<>();
	private Stack<CompilationUnitModel> currentCU = new Stack<>();
	private Stack<TypeModel> currentType = new Stack<>();
	private Stack<MethodModel> currentMethod = new Stack<>();
	
	public ModelBuilder(String projectName, String packageName, String compilationUnitName) {
		// TODO 
		ProjectModel pj = new ProjectModel(projectName);
		PackageModel pack = new PackageModel(packageName);
		pj.addPackage(pack);
		CompilationUnitModel cu = new CompilationUnitModel(compilationUnitName);
		pack.addCompilationUnit(cu);
		
		currentProject = pj;
		currentPackage.push(pack);
		currentCU.push(cu);
	}

	public void analyzeNode(TypeDeclaration type) {
		TypeModel typeModel = new TypeModel(type.getName().getIdentifier());
		analyzeModifier(type.modifiers(), typeModel);

		currentCU.peek().addType(typeModel);
		currentType.push(typeModel);
	}
	public void analyzeNode(EnumDeclaration enumDec) {
		TypeModel typeModel = new TypeModel(enumDec.getName().getIdentifier());
		analyzeModifier(enumDec.modifiers(), typeModel);

		currentCU.peek().addType(typeModel);
		currentType.push(typeModel);
	}
	public void analyzeNodeEnd(AbstractTypeDeclaration type) {
		currentType.pop();
	}

	public void analyzeNode(AnonymousClassDeclaration type) {
		TypeModel typeModel = new TypeModel("[Anonymous Class in "
				+ currentType.peek().getName() + "#" + currentMethod.peek().getName() + "]");

		currentCU.peek().addType(typeModel);
		currentType.push(typeModel);
	}
	public void analyzeNodeEnd(AnonymousClassDeclaration type) {
		currentType.pop();
	}

	public void analyzeNode(MethodDeclaration method) {
		MethodModel methodModel = new MethodModel(method.getName().getFullyQualifiedName());
		analyzeModifier(method.modifiers(), methodModel);

		// 戻り値の型の情報を取得
		Type returnType = method.getReturnType2();
		methodModel.setReturnType(returnType.toString());
		
		// 引数の情報を取得
		@SuppressWarnings("unchecked")
		List<SingleVariableDeclaration> params = method.parameters();
		for (SingleVariableDeclaration param : params) {
			String paramName = param.getName().getIdentifier();
			String paramType = param.getType().toString();
			MethodParameterModel paramModel = new MethodParameterModel(paramName);
			paramModel.setType(paramType);
			paramModel.setText(param.toString());
			methodModel.addParam(paramModel);
			analyzeModifier(param.modifiers(), paramModel);
		}
		
		currentType.peek().addMethod(methodModel);
		currentMethod.push(methodModel);
	}
	public void analyzeNodeEnd(MethodDeclaration method) {
		currentMethod.pop();
	}

	public void analyzeNode(ReturnStatement returnStatement) {
		currentMethod.peek().addMethodReturn(returnStatement.getExpression().toString());
	}
	
	public void analyzeNode(MethodInvocation invocation) {
		currentMethod.peek().addMethodInvocation(invocation.getName().getIdentifier());
		
		// メソッド呼び出しの引数情報を取得
//		List<Expression> args = invocation.arguments();
//		for(Expression arg : args) {
//			createNode("arg", invocationNode).setAttribute("expression", arg.toString());
//		}
//		createTextNode(invocationNode).setTextContent(invocation.toString());
//		
//		currentNode = invocationNode;
	}

	
//	public void analyzeNode(EnumConstantDeclaration enumDec) {
//		Element typeNode = createNode("enumConst", currentNode);
//		typeNode.setAttribute("name", enumDec.getName().getIdentifier());
//
//		analyzeModifier(enumDec.modifiers(), typeNode);
//
//		currentNode = typeNode;
//	}


	private void analyzeModifier(List modifiers, AbstractModifiableModel model) {
		for (Object modifier : modifiers) {
			// 型につけられたアノテーション情報の取得
			if (modifier instanceof Annotation) {
				Annotation annotation = (Annotation) modifier;
				AnnotationModel annotationModel = new AnnotationModel(annotation.getTypeName()
						.toString());
				model.addAnnotation(annotationModel);

				if (annotation instanceof NormalAnnotation) {
					// 通常のアノテーションの場合
					NormalAnnotation na = (NormalAnnotation) annotation;
					@SuppressWarnings("unchecked")
					List<MemberValuePair> valuePairs = na.values();
					for (MemberValuePair valuePair : valuePairs) {
						String paramName = valuePair.getName().getIdentifier();
						String paramValue = valuePair.getValue().toString();
						annotationModel.addParam(paramName, paramValue);
					}
				} else if (annotation instanceof SingleMemberAnnotation) {
					// 暗黙のvalueのみを持つアノテーションの場合
					SingleMemberAnnotation sma = (SingleMemberAnnotation) annotation;
					String paramName = "value";
					String paramValue = sma.getValue().toString();
					annotationModel.addParam(paramName, paramValue);
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
