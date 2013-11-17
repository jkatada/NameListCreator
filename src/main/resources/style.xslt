<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html lang="ja">
			<head>
				<style>
					table {
					  border-top:1px solid;
                      border-left:1px solid;
                      border-collapse:collapse;
					  border-spacing:0;
					  empty-cells:show;
					}
					td, th {
					  padding:0.3em 1em;
					  border-right:1px solid;
    				  border-bottom:1px solid;
					}
				</style>
				<title>
					名称一覧
				</title>
			</head>
			<body>
				<h1>
					名称一覧
				</h1>
				<table>
					<tr>
						<th>型名</th>
						<th>value</th>
						<th>params</th>
						<th>method</th>
						<th>メソッド名</th>
					</tr>
					<xsl:apply-templates select="namelist" />
				</table>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="namelist">
		<xsl:for-each select="type">
			<tr>
				<td>
					<xsl:value-of select="@name" />
				</td>

				<xsl:for-each select="method">
					<tr>
						<td><xsl:value-of select="annotation[@name='RequestMapping']/param[@name='value']/@value" /></td>
						<td><xsl:value-of select="annotation[@name='RequestMapping']/param[@name='params']/@value" /></td>
						<td><xsl:value-of select="annotation[@name='RequestMapping']/param[@name='method']/@value" /></td>
						<td><xsl:value-of select="@name" /></td>

		  				<xsl:for-each select="return">
		  					<tr>
		  						<td><xsl:value-of select="@return"/></td>
		  					</tr>
						</xsl:for-each>

					</tr>
				</xsl:for-each>
	
            </tr>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>