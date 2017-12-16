<?xml version="1.0" encoding="UTF-8"?>
<!--
    (c) by Naoki Nose, Eric Lavarde 2006-2008
    This code is licensed under the GPLv2 or later.
    (http://www.gnu.org/copyleft/gpl.html)
    Stylesheet to transform a FreeMind map into an Excel sheet, use menu point
    File -> Export -> Using XSLT... to choose this XSL file, and name the
    ExportFile Something.xls or Something.xml.
    2006-12-10: added support for notes and attributes (EWL)
    2008-10-23: corrected issue with ss namespace not being output (EWL)
-->
<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:duss="urn:schemas-microsoft-com:office:dummyspreadsheet">
  <xsl:output method="xml" indent="yes" encoding="UTF-8" standalone="yes"/>
  <!-- the duss namespace alias is required in order to be able to output
  	ss:Data properly, Excel ignores the extraneous dummy namespace. -->
  <xsl:namespace-alias stylesheet-prefix="duss" result-prefix="ss"/>

  <xsl:template match="/map">
    <xsl:processing-instruction name="mso-application"> progid="Excel.Sheet"</xsl:processing-instruction>
    <Workbook>
      <Styles>
	<Style ss:ID="s16" ss:Name="attribute_cell">
	<Borders>
	<Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
	<Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
	<Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
	<Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
	</Borders>
	</Style>
	<Style ss:ID="s17" ss:Name="attribute_header">
	<Borders>
	<Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
	<Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
	<Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
	<Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
	</Borders>
	<Font ss:Bold="1"/>
	</Style>
      </Styles>
      <!-- we could probably put something more intelligent as worksheet name,
      	but it would require name mangling to avoid unallowed characters -->
      <Worksheet ss:Name="FreeMind Sheet">
        <Table>
		<xsl:apply-templates select="node">
			<xsl:with-param name="index" select="1" />
		</xsl:apply-templates>
        </Table>
      </Worksheet>
    </Workbook>
  </xsl:template>

<xsl:template match="node">
	<xsl:param name="index" />
	<Row><Cell ss:Index="{$index}">
		<xsl:call-template name="output-node-text-as-data" />
	</Cell>
	<xsl:if test="attribute">
		<Cell ss:StyleID="s17">
			<Data ss:Type="String">Names</Data></Cell>
		<Cell ss:StyleID="s17">
			<Data ss:Type="String">Values</Data></Cell>
	</xsl:if>
	</Row>
	<xsl:apply-templates select="attribute">
		<xsl:with-param name="index" select="$index + 1" />
	</xsl:apply-templates>
	<xsl:apply-templates select="node">
		<xsl:with-param name="index" select="$index + 1" />
	</xsl:apply-templates>
</xsl:template>

<xsl:template match="attribute">
	<xsl:param name="index" />
	<Row><Cell ss:Index="{$index}" ss:StyleID="s16">
		<Data ss:Type="String"><xsl:value-of select="@NAME" /></Data>
	     </Cell>
	     <Cell ss:StyleID="s16">
		<Data ss:Type="String"><xsl:value-of select="@VALUE" /></Data>
	     </Cell>
	</Row>
</xsl:template>

<xsl:template name="output-node-text-as-data">
	<xsl:choose>
	<xsl:when test="richcontent[@TYPE='NODE']">
	<!-- see comments about rich text and HTML format below -->
		<duss:Data ss:Type="String" xmlns="http://www.w3.org/TR/REC-html40"><xsl:copy-of select="richcontent[@TYPE='NODE']/html/body/*" /></duss:Data>
	</xsl:when>
	<xsl:otherwise>
	      <Data ss:Type="String"><xsl:value-of select="@TEXT"/></Data>
	<!-- xsl:value-of select="normalize-space(@TEXT)" / -->
	</xsl:otherwise>
	</xsl:choose>
	<xsl:call-template name="output-note-text-as-comment" />
</xsl:template>

<!-- export of rich text in HTML format should work, but formatting is lost
	because Excel understands only HTML tags in capitals, whereas
	FreeMind exports in small caps. This can probably be solved but would
	require some more tweaking -->
<xsl:template name="output-note-text-as-comment">
	<xsl:if test="richcontent[@TYPE='NOTE']">
	<Comment><duss:Data xmlns="http://www.w3.org/TR/REC-html40"><xsl:copy-of
			select="richcontent[@TYPE='NOTE']/html/body/*" /></duss:Data></Comment>
	</xsl:if>
</xsl:template>

</xsl:stylesheet>

 	  	 
