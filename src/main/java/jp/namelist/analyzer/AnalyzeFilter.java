package jp.namelist.analyzer;

import org.eclipse.jdt.core.dom.MethodInvocation;

public class AnalyzeFilter {

	public boolean isTarget(MethodInvocation node) {
		// model.addAttributeの呼び出しを絞込
		return "addAttribute".equals(node.getName().getIdentifier());
	}
	
}
