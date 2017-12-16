<?xml version="1.0" encoding="UTF-8"?>
	<!--
		/*FreeMind - A Program for creating and viewing Mindmaps *Copyright
		(C) 2000-2008 Christian Foltin and others. * *See COPYING for Details
		* *This program is free software; you can redistribute it and/or
		*modify it under the terms of the GNU General Public License *as
		published by the Free Software Foundation; either version 2 *of the
		License, or (at your option) any later version. * *This program is
		distributed in the hope that it will be useful, *but WITHOUT ANY
		WARRANTY; without even the implied warranty of *MERCHANTABILITY or
		FITNESS FOR A PARTICULAR PURPOSE. See the *GNU General Public License
		for more details. * *You should have received a copy of the GNU
		General Public License *along with this program; if not, write to the
		Free Software *Foundation, Inc., 59 Temple Place - Suite 330, Boston,
		MA 02111-1307, USA. * */
	-->
<xsl:stylesheet version="1.0" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:presentation="urn:oasis:names:tc:opendocument:xmlns:presentation:1.0"
	xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0"
	xmlns:style="urn:oasis:names:tc:opendocument:xmlns:style:1.0"
	xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0"
	xmlns:table="urn:oasis:names:tc:opendocument:xmlns:table:1.0"
	xmlns:draw="urn:oasis:names:tc:opendocument:xmlns:drawing:1.0"
	xmlns:fo="urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0"
	xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:dc="http://purl.org/dc/elements/1.1/"
	xmlns:meta="urn:oasis:names:tc:opendocument:xmlns:meta:1.0"
	xmlns:number="urn:oasis:names:tc:opendocument:xmlns:datastyle:1.0"
	xmlns:svg="urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0"
	xmlns:chart="urn:oasis:names:tc:opendocument:xmlns:chart:1.0"
	xmlns:dr3d="urn:oasis:names:tc:opendocument:xmlns:dr3d:1.0" xmlns:math="http://www.w3.org/1998/Math/MathML"
	xmlns:form="urn:oasis:names:tc:opendocument:xmlns:form:1.0"
	xmlns:script="urn:oasis:names:tc:opendocument:xmlns:script:1.0"
	xmlns:ooo="http://openoffice.org/2004/office" xmlns:ooow="http://openoffice.org/2004/writer"
	xmlns:oooc="http://openoffice.org/2004/calc" xmlns:dom="http://www.w3.org/2001/xml-events"
	xmlns:xforms="http://www.w3.org/2002/xforms" xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<xsl:output method="xml" version="1.0" indent="no"
		encoding="UTF-8" omit-xml-declaration="no" />
	<xsl:strip-space elements="*" />

	<!-- fc, 16.2.2009: The following parameter is set by freemind. -->
	<xsl:param name="date">
		02.2009
	</xsl:param>

	<xsl:template match="map">
		<office:document-content
			xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0"
			xmlns:style="urn:oasis:names:tc:opendocument:xmlns:style:1.0"
			xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0"
			xmlns:table="urn:oasis:names:tc:opendocument:xmlns:table:1.0"
			xmlns:draw="urn:oasis:names:tc:opendocument:xmlns:drawing:1.0"
			xmlns:fo="urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0"
			xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:dc="http://purl.org/dc/elements/1.1/"
			xmlns:meta="urn:oasis:names:tc:opendocument:xmlns:meta:1.0"
			xmlns:number="urn:oasis:names:tc:opendocument:xmlns:datastyle:1.0"
			xmlns:presentation="urn:oasis:names:tc:opendocument:xmlns:presentation:1.0"
			xmlns:svg="urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0"
			xmlns:chart="urn:oasis:names:tc:opendocument:xmlns:chart:1.0"
			xmlns:dr3d="urn:oasis:names:tc:opendocument:xmlns:dr3d:1.0"
			xmlns:math="http://www.w3.org/1998/Math/MathML" xmlns:form="urn:oasis:names:tc:opendocument:xmlns:form:1.0"
			xmlns:script="urn:oasis:names:tc:opendocument:xmlns:script:1.0"
			xmlns:ooo="http://openoffice.org/2004/office" xmlns:ooow="http://openoffice.org/2004/writer"
			xmlns:oooc="http://openoffice.org/2004/calc" xmlns:dom="http://www.w3.org/2001/xml-events"
			xmlns:xforms="http://www.w3.org/2002/xforms" xmlns:xsd="http://www.w3.org/2001/XMLSchema"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:smil="urn:oasis:names:tc:opendocument:xmlns:smil-compatible:1.0"
			xmlns:anim="urn:oasis:names:tc:opendocument:xmlns:animation:1.0"
			xmlns:field="urn:openoffice:names:experimental:ooxml-odf-interop:xmlns:field:1.0"
			office:version="1.1">
			<office:scripts />
			<office:automatic-styles>
				<style:style style:name="dp1" style:family="drawing-page">
					<style:drawing-page-properties
						presentation:background-visible="true"
						presentation:background-objects-visible="true"
						presentation:display-footer="false"
						presentation:display-page-number="false"
						presentation:display-date-time="false" />
				</style:style>
				<style:style style:name="dp2" style:family="drawing-page">
					<style:drawing-page-properties
						presentation:display-header="true" presentation:display-footer="true"
						presentation:display-page-number="false"
						presentation:display-date-time="true" />
				</style:style>
				<style:style style:name="dp3" style:family="drawing-page">
					<style:drawing-page-properties
						presentation:background-visible="true"
						presentation:background-objects-visible="true"
						presentation:display-footer="true"
						presentation:display-page-number="true"
						presentation:display-date-time="true" />
				</style:style>
				<style:style style:name="gr1" style:family="graphic">
					<style:graphic-properties style:protect="size" />
				</style:style>
				<style:style style:name="pr1" style:family="presentation"
					style:parent-style-name="Standard-title">
					<style:graphic-properties draw:fill-color="#ffffff"
						fo:min-height="3.256cm" />
				</style:style>
				<style:style style:name="pr2" style:family="presentation"
					style:parent-style-name="Standard-subtitle">
					<style:graphic-properties draw:fill-color="#ffffff"
						fo:min-height="13.609cm" />
				</style:style>
				<style:style style:name="pr3" style:family="presentation"
					style:parent-style-name="Standard-notes">
					<style:graphic-properties draw:fill-color="#ffffff"
						draw:auto-grow-height="true" fo:min-height="13.365cm" />
				</style:style>
				<style:style style:name="pr4" style:family="presentation"
					style:parent-style-name="Standard-outline1">
					<style:graphic-properties draw:fill-color="#ffffff"
						fo:min-height="13.609cm" />
				</style:style>
				<style:style style:name="Title1" style:family="paragraph">
					<style:paragraph-properties
						fo:margin-left="0cm" fo:margin-right="0cm" fo:text-align="start"
						fo:text-indent="0cm" />
				</style:style>
				<style:style style:name="T1" style:family="text">
					<style:text-properties fo:font-size="36pt"
						style:font-size-asian="36pt" style:font-size-complex="36pt" />
				</style:style>
			    <style:style style:name="P1" style:family="paragraph">
			      <style:paragraph-properties fo:margin-left="1.2cm" fo:margin-right="0cm" fo:text-indent="-0.9cm"/>
			    </style:style>
			    <style:style style:name="P2" style:family="paragraph">
			      <style:paragraph-properties fo:margin-left="2.4cm" fo:margin-right="0cm" fo:text-indent="-0.8cm"/>
			    </style:style>
			    <style:style style:name="P3" style:family="paragraph">
			      <style:paragraph-properties fo:margin-left="3.6cm" fo:margin-right="0cm" fo:text-indent="-0.6cm"/>
			    </style:style>
			    <style:style style:name="P4" style:family="paragraph">
			      <style:paragraph-properties fo:margin-left="4.8cm" fo:margin-right="0cm" fo:text-indent="-0.6cm"/>
			    </style:style>
			    <style:style style:name="P5" style:family="paragraph">
			      <style:paragraph-properties fo:margin-left="6cm" fo:margin-right="0cm" fo:text-indent="-0.6cm"/>
			    </style:style>
			    <style:style style:name="P6" style:family="paragraph">
			      <style:paragraph-properties fo:margin-left="7.2cm" fo:margin-right="0cm" fo:text-indent="-0.6cm"/>
			    </style:style>
			    <style:style style:name="P7" style:family="paragraph">
			      <style:paragraph-properties fo:margin-left="8.4cm" fo:margin-right="0cm" fo:text-indent="-0.6cm"/>
			    </style:style>
			    <style:style style:name="P8" style:family="paragraph">
			      <style:paragraph-properties fo:margin-left="9.6cm" fo:margin-right="0cm" fo:text-indent="-0.6cm"/>
			    </style:style>
			    <style:style style:name="P9" style:family="paragraph">
			      <style:paragraph-properties fo:margin-left="10.8cm" fo:margin-right="0cm" fo:text-indent="-0.6cm"/>
			    </style:style>
				<text:list-style style:name="L1">
					<text:list-level-style-bullet
						text:level="1" text:bullet-char="●">
						<style:text-properties fo:font-family="StarSymbol"
							style:use-window-font-color="true" fo:font-size="45%" />
					</text:list-level-style-bullet>
					<text:list-level-style-bullet
						text:level="2" text:bullet-char="–">
						<style:list-level-properties
							text:space-before="1.6cm" text:min-label-width="0.8cm" />
						<style:text-properties fo:font-family="StarSymbol"
							style:use-window-font-color="true" fo:font-size="75%" />
					</text:list-level-style-bullet>
					<text:list-level-style-bullet
						text:level="3" text:bullet-char="●">
						<style:list-level-properties
							text:space-before="3cm" text:min-label-width="0.6cm" />
						<style:text-properties fo:font-family="StarSymbol"
							style:use-window-font-color="true" fo:font-size="45%" />
					</text:list-level-style-bullet>
					<text:list-level-style-bullet
						text:level="4" text:bullet-char="–">
						<style:list-level-properties
							text:space-before="4.2cm" text:min-label-width="0.6cm" />
						<style:text-properties fo:font-family="StarSymbol"
							style:use-window-font-color="true" fo:font-size="75%" />
					</text:list-level-style-bullet>
					<text:list-level-style-bullet
						text:level="5" text:bullet-char="●">
						<style:list-level-properties
							text:space-before="5.4cm" text:min-label-width="0.6cm" />
						<style:text-properties fo:font-family="StarSymbol"
							style:use-window-font-color="true" fo:font-size="45%" />
					</text:list-level-style-bullet>
					<text:list-level-style-bullet
						text:level="6" text:bullet-char="●">
						<style:list-level-properties
							text:space-before="6.6cm" text:min-label-width="0.6cm" />
						<style:text-properties fo:font-family="StarSymbol"
							style:use-window-font-color="true" fo:font-size="45%" />
					</text:list-level-style-bullet>
					<text:list-level-style-bullet
						text:level="7" text:bullet-char="●">
						<style:list-level-properties
							text:space-before="7.8cm" text:min-label-width="0.6cm" />
						<style:text-properties fo:font-family="StarSymbol"
							style:use-window-font-color="true" fo:font-size="45%" />
					</text:list-level-style-bullet>
					<text:list-level-style-bullet
						text:level="8" text:bullet-char="●">
						<style:list-level-properties
							text:space-before="9cm" text:min-label-width="0.6cm" />
						<style:text-properties fo:font-family="StarSymbol"
							style:use-window-font-color="true" fo:font-size="45%" />
					</text:list-level-style-bullet>
					<text:list-level-style-bullet
						text:level="9" text:bullet-char="●">
						<style:list-level-properties
							text:space-before="10.2cm" text:min-label-width="0.6cm" />
						<style:text-properties fo:font-family="StarSymbol"
							style:use-window-font-color="true" fo:font-size="45%" />
					</text:list-level-style-bullet>
				</text:list-style>
			</office:automatic-styles>
			<office:body>
				<office:presentation>
					<!-- Title page -->
					<xsl:apply-templates select="node" mode="titlepage" />
					<presentation:settings
						presentation:mouse-visible="false" />
				</office:presentation>
			</office:body>
		</office:document-content>
	</xsl:template>

	<xsl:template match="node" mode="titlepage">
		<presentation:footer-decl presentation:name="ftr1">
			<xsl:call-template name="output-nodecontent">
				<xsl:with-param name="style"></xsl:with-param>
			</xsl:call-template>
		</presentation:footer-decl>
		<presentation:date-time-decl
			presentation:name="dtd1" presentation:source="fixed">
			<xsl:value-of select="$date" />
		</presentation:date-time-decl>
		<draw:page draw:name="page1" draw:style-name="dp1"
			draw:master-page-name="Standard"
			presentation:presentation-page-layout-name="AL1T0"
			presentation:use-footer-name="ftr1" presentation:use-date-time-name="dtd1">
			<office:forms form:automatic-focus="false"
				form:apply-design-mode="false" />
			<draw:frame presentation:style-name="pr1" draw:layer="layout"
				svg:width="25.199cm" svg:height="3.256cm" svg:x="1.4cm" svg:y="0.962cm"
				presentation:class="title">
				<draw:text-box>
					<xsl:call-template name="output-nodecontent">
						<xsl:with-param name="style">P1</xsl:with-param>
					</xsl:call-template>
				</draw:text-box>
			</draw:frame>
			<draw:frame presentation:style-name="pr2" draw:layer="layout"
				svg:width="25.199cm" svg:height="13.609cm" svg:x="1.4cm" svg:y="5.039cm"
				presentation:class="subtitle" presentation:placeholder="true">
				<draw:text-box />
			</draw:frame>
			<presentation:notes draw:style-name="dp2">
				<office:forms form:automatic-focus="false"
					form:apply-design-mode="false" />
				<draw:page-thumbnail draw:style-name="gr1"
					draw:layer="layout" svg:width="14.848cm" svg:height="11.135cm"
					svg:x="3.075cm" svg:y="2.257cm" draw:page-number="1"
					presentation:class="page" />
				<draw:frame presentation:style-name="pr3"
					draw:text-style-name="P2" draw:layer="layout" svg:width="16.799cm"
					svg:height="13.365cm" svg:x="2.1cm" svg:y="14.107cm"
					presentation:class="notes" presentation:placeholder="true">
					<draw:text-box />
				</draw:frame>
			</presentation:notes>
		</draw:page>
		<!-- Give the other nodes out. -->
		<xsl:apply-templates select="." mode="toc" />
	</xsl:template>

	<xsl:template match="node" mode="titleout">
		<xsl:call-template name="output-nodecontent">
			<xsl:with-param name="style"></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="node" mode="contentout">
		<text:list text:style-name="L2">
			<text:list-item>
				<xsl:call-template name="output-nodecontent">
					<xsl:with-param name="style">P1</xsl:with-param>
				</xsl:call-template>
			</text:list-item>
		</text:list>
	</xsl:template>

	<xsl:template name="depthMeasurement">
		<xsl:param name="nodes" select="."></xsl:param>
        <xsl:choose>
        	<xsl:when test="count($nodes)=0">0</xsl:when>
        	<xsl:when test="count($nodes)=1">
	        	<!-- Call method with its descendants. -->
        		<xsl:variable name="val">
	        		<xsl:call-template name="depthMeasurement">
	        			<xsl:with-param name="nodes" select="$nodes/node"/>
	        		</xsl:call-template>
        		</xsl:variable>
        		<xsl:value-of select="$val+1"/>
        	</xsl:when>
	        <xsl:otherwise>
        		<!-- Determine max -->
		        <xsl:variable name="half" select="floor(count($nodes) div 2)"/>
        		<xsl:variable name="val1">
	        		<xsl:call-template name="depthMeasurement">
	        			<xsl:with-param name="nodes" select="$nodes[position() &lt;= $half]"/>
	        		</xsl:call-template>
        		</xsl:variable>
        		<xsl:variable name="val2">
	        		<xsl:call-template name="depthMeasurement">
	        			<xsl:with-param name="nodes" select="$nodes[position() > $half]"/>
	        		</xsl:call-template>
        		</xsl:variable>
        		<xsl:choose>
        			<xsl:when test="$val1 &gt; $val2"><xsl:value-of select="$val1"/></xsl:when>
        			<xsl:otherwise><xsl:value-of select="$val2"/></xsl:otherwise>
        		</xsl:choose>
	        </xsl:otherwise>
        </xsl:choose>
	</xsl:template>


	<xsl:template match="node" mode="toc">
		<xsl:variable name="depth">
			<xsl:call-template name="depthMeasurement">
				<xsl:with-param name="nodes" select="."></xsl:with-param>
			</xsl:call-template>
		</xsl:variable>
		<!--
			If the current node has a note, then insert a special page for it.
		-->
		<xsl:if test="richcontent[@TYPE='NOTE']">
			<xsl:apply-templates select="." mode="note" />
		</xsl:if>
		<xsl:variable name="subnodes" select="node"></xsl:variable>
		<xsl:variable name="completeOut" select="$depth &lt; 3 or @FOLDED='true'"></xsl:variable>
		<xsl:choose>
			<xsl:when test="$subnodes = 0"></xsl:when>
			<xsl:otherwise>
				<!--
					Give summary page out. Title is the current node, Content are the
					children, notes are omitted (draw:name="page3" is omitted, too).
				-->
				<draw:page draw:style-name="dp3"
					draw:master-page-name="Standard"
					presentation:presentation-page-layout-name="AL2T1"
					presentation:use-footer-name="ftr1" presentation:use-date-time-name="dtd1">
					<office:forms form:automatic-focus="false"
						form:apply-design-mode="false" />
					<draw:frame presentation:style-name="pr1"
						draw:text-style-name="P4" draw:layer="layout" svg:width="25.199cm"
						svg:height="3.256cm" svg:x="1.4cm" svg:y="0.962cm"
						presentation:class="title" presentation:user-transformed="true">
						<draw:text-box>
							<text:p text:style-name="Title1">
								<!-- Title of upper slide -->
								<xsl:apply-templates select=".." mode="titleout" />
								<text:line-break /><text:span text:style-name="T1">
									<!-- Title of this slide with decreased font -->
									<xsl:call-template name="output-nodecontent">
										<xsl:with-param name="style"></xsl:with-param>
									</xsl:call-template>
								</text:span>
							</text:p>
						</draw:text-box>
					</draw:frame>
					<draw:frame presentation:style-name="pr4" draw:layer="layout"
						svg:width="25.199cm" svg:height="13.609cm" svg:x="1.4cm" svg:y="4.914cm"
						presentation:class="outline">
						<xsl:choose>
							<xsl:when test="$completeOut">
								<!-- Give the complete sub nodes out. -->
								<draw:text-box>
									<xsl:apply-templates select="node" />
								</draw:text-box>
							</xsl:when>
							<xsl:otherwise>
								<draw:text-box>
									<xsl:apply-templates select="node" mode="contentout" />
								</draw:text-box>
							</xsl:otherwise>
						</xsl:choose>
					</draw:frame>
					<presentation:notes draw:style-name="dp2">
						<office:forms form:automatic-focus="false"
							form:apply-design-mode="false" />
						<draw:page-thumbnail draw:style-name="gr1"
							draw:layer="layout" svg:width="14.848cm" svg:height="11.135cm"
							svg:x="3.075cm" svg:y="2.257cm"
							presentation:class="page" />
						<draw:frame presentation:style-name="pr3"
							draw:text-style-name="P7" draw:layer="layout" svg:width="16.799cm"
							svg:height="13.365cm" svg:x="2.1cm" svg:y="14.107cm"
							presentation:class="notes" presentation:placeholder="true">
							<draw:text-box />
						</draw:frame>
					</presentation:notes>
				</draw:page>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:choose>		
			<xsl:when test="$completeOut"/>
			<xsl:otherwise>
				<xsl:apply-templates select="node" mode="toc" />
			</xsl:otherwise>
		</xsl:choose>		
	</xsl:template>

	<xsl:template match="node">
		<xsl:param name="depth">0</xsl:param>
		<xsl:variable name="text">
			<xsl:call-template name="output-nodecontent">
				<xsl:with-param name="style">P<xsl:value-of select="$depth+1"/></xsl:with-param>
			</xsl:call-template>
		</xsl:variable>
		<text:list text:style-name="L2">
			<text:list-item>
				<xsl:call-template name="insertList">
					<xsl:with-param name="text" select="$text"/>
					<xsl:with-param name="depth" select="$depth"/>
				</xsl:call-template>
			</text:list-item>
		</text:list>
		<xsl:apply-templates select="node" >
			<xsl:with-param name="depth" select="$depth+1"></xsl:with-param>
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template name="insertList">
		<xsl:param name="text">TT</xsl:param>
		<xsl:param name="depth">0</xsl:param>
		<xsl:choose>
			<xsl:when test="$depth = 0"><xsl:copy-of select="$text"/></xsl:when>
			<xsl:otherwise>
				<text:list>
					<text:list-item>
						<xsl:call-template name="insertList">
							<xsl:with-param name="text" select="$text"/>
							<xsl:with-param name="depth" select="$depth - 1"/>
						</xsl:call-template>
					</text:list-item>
				</text:list>
			</xsl:otherwise>
		</xsl:choose>		
	</xsl:template>

	<xsl:template match="hook" />


	<!-- Give links out. -->
	<xsl:template match="@LINK">
		<text:p text:style-name="Standard">
			<xsl:element name="text:a" namespace="text">
				<xsl:attribute namespace="xlink" name="xlink:type">simple</xsl:attribute>
				<xsl:attribute namespace="xlink" name="xlink:href">
					<!-- Convert relative links, such that they start with "../". 
					     This is due to the fact, that OOo calculates all relative links from the document itself! -->
					<xsl:choose>
						<xsl:when test="starts-with(.,'/') or contains(.,':')">
							<!-- absolute link -->
							<xsl:value-of select="." />
						</xsl:when>
						<xsl:otherwise>
							<!-- relative link, put "../" at the front -->
							<xsl:text>../</xsl:text><xsl:value-of select="." />
						</xsl:otherwise>
					</xsl:choose>
				</xsl:attribute>
				<xsl:value-of select="." />
			</xsl:element>
		</text:p>
	</xsl:template>

	<xsl:template name="output-nodecontent">
		<xsl:param name="style">Standard</xsl:param>
		<xsl:choose>
			<xsl:when test="richcontent[@TYPE='NODE']">
				<xsl:apply-templates select="richcontent[@TYPE='NODE']/html/body"
					mode="richcontent">
					<xsl:with-param name="style" select="$style" />
				</xsl:apply-templates>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="$style = ''">
						<!--no style for headings. -->
						<xsl:call-template name="textnode" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:element name="text:p">
							<xsl:attribute name="text:style-name"><xsl:value-of
								select="$style" /></xsl:attribute>
							<xsl:call-template name="textnode" />
						</xsl:element>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template> <!-- xsl:template name="output-nodecontent" -->

	<xsl:template name="output-notecontent">
		<xsl:if test="richcontent[@TYPE='NOTE']">
			<xsl:apply-templates select="richcontent[@TYPE='NOTE']/html/body"
				mode="richcontent">
				<xsl:with-param name="style">Standard</xsl:with-param>
			</xsl:apply-templates>
		</xsl:if>
	</xsl:template> <!-- xsl:template name="output-note" -->


	<xsl:template name="textnode">
		<xsl:call-template name="format_text">
			<xsl:with-param name="nodetext">
				<xsl:choose>
					<xsl:when test="@TEXT = ''">
						<xsl:text> </xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="@TEXT" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template> <!-- xsl:template name="textnode" -->


	<!-- replace ASCII line breaks through ODF line breaks (br) -->
	<xsl:template name="format_text">
		<xsl:param name="nodetext"></xsl:param>
		<xsl:if test="string-length(substring-after($nodetext,'&#xa;')) = 0">
			<xsl:value-of select="$nodetext" />
		</xsl:if>
		<xsl:if test="string-length(substring-after($nodetext,'&#xa;')) > 0">
			<xsl:value-of select="substring-before($nodetext,'&#xa;')" />
			<text:line-break />
			<xsl:call-template name="format_text">
				<xsl:with-param name="nodetext">
					<xsl:value-of select="substring-after($nodetext,'&#xa;')" />
				</xsl:with-param>
			</xsl:call-template>
		</xsl:if>
	</xsl:template> <!-- xsl:template name="format_text" -->

	<xsl:template match="body" mode="richcontent">
		<xsl:param name="style">Standard</xsl:param>
		<!--       <xsl:copy-of select="string(.)"/> -->
		<xsl:apply-templates select="text()|*" mode="richcontent">
			<xsl:with-param name="style" select="$style"></xsl:with-param>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="text()" mode="richcontent">
		<xsl:copy-of select="string(.)" />
	</xsl:template>
	<xsl:template match="br" mode="richcontent">
		<text:line-break />
	</xsl:template>
	<xsl:template match="b" mode="richcontent">
		<xsl:param name="style">Standard</xsl:param>
		<text:span text:style-name="T1">
			<xsl:apply-templates select="text()|*" mode="richcontent">
				<xsl:with-param name="style" select="$style"></xsl:with-param>
			</xsl:apply-templates>
		</text:span>
	</xsl:template>
	<xsl:template match="p" mode="richcontent">
		<xsl:param name="style">Standard</xsl:param>
		<xsl:choose>
			<xsl:when test="$style = ''">
				<xsl:apply-templates select="text()|*" mode="richcontent">
					<xsl:with-param name="style" select="$style"></xsl:with-param>
				</xsl:apply-templates>
			</xsl:when>
			<xsl:when test="@style='text-align: center'">
				<text:p text:style-name="P3">
					<xsl:apply-templates select="text()|*" mode="richcontent">
						<xsl:with-param name="style" select="$style"></xsl:with-param>
					</xsl:apply-templates>
				</text:p>
			</xsl:when>
			<xsl:when test="@style='text-align: right'">
				<text:p text:style-name="P4">
					<xsl:apply-templates select="text()|*" mode="richcontent">
						<xsl:with-param name="style" select="$style"></xsl:with-param>
					</xsl:apply-templates>
				</text:p>
			</xsl:when>
			<xsl:when test="@style='text-align: justify'">
				<text:p text:style-name="P5">
					<xsl:apply-templates select="text()|*" mode="richcontent">
						<xsl:with-param name="style" select="$style"></xsl:with-param>
					</xsl:apply-templates>
				</text:p>
			</xsl:when>
			<xsl:otherwise>
				<xsl:element name="text:p">
					<xsl:attribute name="text:style-name"><xsl:value-of
						select="$style" /></xsl:attribute>
					<xsl:apply-templates select="text()|*" mode="richcontent">
						<xsl:with-param name="style" select="$style"></xsl:with-param>
					</xsl:apply-templates>
				</xsl:element>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="i" mode="richcontent">
		<xsl:param name="style">Standard</xsl:param>
		<text:span text:style-name="T2">
			<xsl:apply-templates select="text()|*" mode="richcontent">
				<xsl:with-param name="style" select="$style"></xsl:with-param>
			</xsl:apply-templates>
		</text:span>
	</xsl:template>
	<xsl:template match="u" mode="richcontent">
		<xsl:param name="style">Standard</xsl:param>
		<text:span text:style-name="T3">
			<xsl:apply-templates select="text()|*" mode="richcontent">
				<xsl:with-param name="style" select="$style"></xsl:with-param>
			</xsl:apply-templates>
		</text:span>
	</xsl:template>
	<xsl:template match="ul" mode="richcontent">
		<xsl:param name="style">Standard</xsl:param>
		<text:list text:style-name="L1">
			<xsl:apply-templates select="text()|*" mode="richcontentul">
				<xsl:with-param name="style" select="$style"></xsl:with-param>
			</xsl:apply-templates>
		</text:list>
		<text:p text:style-name="P3" />
	</xsl:template>
	<xsl:template match="ol" mode="richcontent">
		<xsl:param name="style">Standard</xsl:param>
		<text:list text:style-name="L2">
			<xsl:apply-templates select="text()|*" mode="richcontentol">
				<xsl:with-param name="style" select="$style"></xsl:with-param>
			</xsl:apply-templates>
		</text:list>
		<text:p text:style-name="P3" />
	</xsl:template>
	<xsl:template match="li" mode="richcontentul">
		<xsl:param name="style">Standard</xsl:param>
		<text:list-item>
			<text:p text:style-name="P1"><!--
			-->
				<xsl:apply-templates select="text()|*" mode="richcontent">
					<xsl:with-param name="style" select="$style"></xsl:with-param>
				</xsl:apply-templates><!--			
		-->
			</text:p>
		</text:list-item>
	</xsl:template>
	<xsl:template match="li" mode="richcontentol">
		<xsl:param name="style">Standard</xsl:param>
		<text:list-item>
			<text:p text:style-name="P2"><!--
			-->
				<xsl:apply-templates select="text()|*" mode="richcontent">
					<xsl:with-param name="style" select="$style"></xsl:with-param>
				</xsl:apply-templates><!--			
		-->
			</text:p>
		</text:list-item>
	</xsl:template>

	<!--
		<text:list-item> <text:p text:style-name="P1">b </text:list-item>
		<text:list-item> <text:p text:style-name="P1">c</text:p>
		</text:list-item> <text:p text:style-name="P2"/>
	-->
	<!--
		<text:ordered-list text:style-name="L2"> <text:list-item> <text:p
		text:style-name="P3">1</text:p> </text:list-item> <text:list-item>
		<text:p text:style-name="P3">2</text:p> </text:list-item>
		<text:list-item> <text:p text:style-name="P3">3</text:p>
		</text:list-item> </text:ordered-list> <text:p text:style-name="P2"/>
	-->
	<!--
		Table: <table:table table:name="Table1" table:style-name="Table1">
		<table:table-column table:style-name="Table1.A"
		table:number-columns-repeated="3"/> <table:table-row>
		<table:table-cell table:style-name="Table1.A1"
		table:value-type="string"> <text:p text:style-name="Table
		Contents">T11</text:p> </table:table-cell> <table:table-cell
		table:style-name="Table1.A1" table:value-type="string"> <text:p
		text:style-name="Table Contents">T21</text:p> </table:table-cell>
		<table:table-cell table:style-name="Table1.C1"
		table:value-type="string"> <text:p text:style-name="Table
		Contents">T31</text:p> </table:table-cell> </table:table-row>
		<table:table-row> <table:table-cell table:style-name="Table1.A2"
		table:value-type="string"> <text:p text:style-name="Table
		Contents">T12</text:p> </table:table-cell> <table:table-cell
		table:style-name="Table1.A2" table:value-type="string"> <text:p
		text:style-name="Table Contents">T22</text:p> </table:table-cell>
		<table:table-cell table:style-name="Table1.C2"
		table:value-type="string"> <text:p text:style-name="Table
		Contents">T32</text:p> </table:table-cell> </table:table-row>
		<table:table-row> <table:table-cell table:style-name="Table1.A2"
		table:value-type="string"> <text:p text:style-name="Table
		Contents">T13</text:p> </table:table-cell> <table:table-cell
		table:style-name="Table1.A2" table:value-type="string"> <text:p
		text:style-name="Table Contents">T23</text:p> </table:table-cell>
		<table:table-cell table:style-name="Table1.C2"
		table:value-type="string"> <text:p text:style-name="Table
		Contents">T32</text:p> </table:table-cell> </table:table-row>
		</table:table>
	-->


</xsl:stylesheet>
