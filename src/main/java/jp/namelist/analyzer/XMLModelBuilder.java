package jp.namelist.analyzer;

import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLModelBuilder {

	private Document document;

	private Node currentNode;
	
	public XMLModelBuilder() {
		try {
			// XMLのルート要素を作成。
			document = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().getDOMImplementation()
					.createDocument("", "namelist", null);
			currentNode = document.getDocumentElement();
		} catch (ParserConfigurationException e) {
			throw new IllegalStateException(e);
		}
	}

	public void analyzeNode(EnumDeclaration enumDec) {
		// TODO enumにするべき？
		Element typeNode = createNode("type", currentNode);
		typeNode.setAttribute("name", enumDec.getName().getIdentifier());

		analyzeModifier(enumDec.modifiers(), typeNode);

		currentNode = typeNode;
	}

	public void analyzeNode(EnumConstantDeclaration enumDec) {
		Element typeNode = createNode("enumConst", currentNode);
		typeNode.setAttribute("name", enumDec.getName().getIdentifier());

		analyzeModifier(enumDec.modifiers(), typeNode);

		currentNode = typeNode;
	}

	public void analyzeNode(AnonymousClassDeclaration type) {
		Element typeNode = createNode("type", currentNode);
		typeNode.setAttribute("name", "(Anonymous Class)");

		currentNode = typeNode;
	}
	public void analyzeNode(TypeDeclaration type) {
		Element typeNode = createNode("type", currentNode);
		typeNode.setAttribute("name", type.getName().getIdentifier());
		analyzeModifier(type.modifiers(), typeNode);

		currentNode = typeNode;
	}

	private void analyzeModifier(List modifiers, Element parent) {
		for (Object modifier : modifiers) {
			// 型につけられたアノテーション情報の取得
			if (modifier instanceof Annotation) {
				Annotation annotation = (Annotation) modifier;
				Element annotationNode = createNode("annotation", parent);
				annotationNode.setAttribute("name", annotation.getTypeName()
						.toString());

				if (annotation instanceof NormalAnnotation) {
					// 通常のアノテーションの場合
					NormalAnnotation na = (NormalAnnotation) annotation;
					@SuppressWarnings("unchecked")
					List<MemberValuePair> valuePairs = na.values();
					for (MemberValuePair valuePair : valuePairs) {
						Element annotationParamNode = createNode("param",
								annotationNode);
						annotationParamNode.setAttribute("name", valuePair
								.getName().getIdentifier());
						annotationParamNode.setAttribute("value", valuePair
								.getValue().toString());
						annotationNode.appendChild(annotationParamNode);
					}
				} else if (annotation instanceof SingleMemberAnnotation) {
					// 暗黙のvalueのみを持つアノテーションの場合
					SingleMemberAnnotation sma = (SingleMemberAnnotation) annotation;
					Element annotationParamNode = createNode("param",
							annotationNode);
					annotationParamNode.setAttribute("name", "value");
					annotationParamNode.setAttribute("value", sma.getValue()
							.toString());
				}

				// アノテーションのテキスト表現をtextタグに保持
				createTextNode(annotationNode).setTextContent(
						annotation.toString());

				parent.appendChild(annotationNode);
			} else if (modifier instanceof Modifier) {
				// その他のModifier(abstract, publicなど)を記録
				createNode("modifier", parent).setAttribute("value",
						modifier.toString());
			} else {
				throw new IllegalStateException(
						"modifier is not Modifier instance. modifier getClass() : "
								+ modifier.getClass());
			}
		}
	}

	public void analyzeNode(MethodDeclaration method) {
		Element methodNode = createNode("method", currentNode);
		methodNode.setAttribute("name", method.getName().getFullyQualifiedName());
		analyzeModifier(method.modifiers(), methodNode);

		// 戻り値の型の情報を取得
		Type returnType = method.getReturnType2();
		methodNode.setAttribute("returnType", returnType.toString());
		
		// 引数の情報を取得
		@SuppressWarnings("unchecked")
		List<SingleVariableDeclaration> params = method.parameters();
		for (SingleVariableDeclaration param : params) {
			Element paramNode = createNode("param", methodNode);
			paramNode.setAttribute("name", param.getName().getIdentifier());
			paramNode.setAttribute("type", param.getType().toString());
			analyzeModifier(param.modifiers(), paramNode);
		}
		
		currentNode = methodNode;
	}

	public void analyzeNode(ReturnStatement returnStatement) {
		Element returnNode = createNode("return", currentNode);
		returnNode.setAttribute("return", returnStatement.getExpression().toString());
		
		currentNode = returnNode;
	}
	
	public void analyzeNode(MethodInvocation invocation) {
		Element invocationNode = createNode("invocation", currentNode);
		invocationNode.setAttribute("name", invocation.getName().getIdentifier());
		
		// メソッド呼び出しの引数情報を取得
		List<Expression> args = invocation.arguments();
		for(Expression arg : args) {
			createNode("arg", invocationNode).setAttribute("expression", arg.toString());
		}
		createTextNode(invocationNode).setTextContent(invocation.toString());
		
		currentNode = invocationNode;
	}
	
	public boolean upAnalyzeHierarchy() {
		Node parent = currentNode.getParentNode();
		if (parent != null) {
			currentNode = parent;
		}
		
		return (parent != null);
	}

	private Element createNode(String name, Node parent) {
		Element textNode = document.createElement(name);
		if (parent != null) {
			parent.appendChild(textNode);
		}
		return textNode;
	}


	private Element createTextNode(Node parent) {
		return createNode("text", parent);
	}

	public Document getDocument() {
		return document;
	}

}
