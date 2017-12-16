<?xml version="1.0" encoding="UTF-8"?>
<!--
/*FreeMind - A Program for creating and viewing Mindmaps
 *Copyright (C) 2000-2008  Christian Foltin and others.
 *
 *See COPYING for Details
 *
 *This program is free software; you can redistribute it and/or
 *modify it under the terms of the GNU General Public License
 *as published by the Free Software Foundation; either version 2
 *of the License, or (at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program; if not, write to the Free Software
 *Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
-->
<xsl:stylesheet
	version="1.0" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<!--
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0"
	xmlns:style="urn:oasis:names:tc:opendocument:xmlns:style:1.0"
	xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0"
	xmlns:table="urn:oasis:names:tc:opendocument:xmlns:table:1.0"
	xmlns:draw="urn:oasis:names:tc:opendocument:xmlns:drawing:1.0"
	xmlns:fo="urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0"
	xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:dc="http://purl.org/dc/elements/1.1/"
	xmlns:meta="urn:oasis:names:tc:opendocument:xmlns:meta:1.0"
	xmlns:number="urn:oasis:names:tc:opendocument:xmlns:datastyle:1.0"
	xmlns:svg="urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0"
	xmlns:chart="urn:oasis:names:tc:opendocument:xmlns:chart:1.0"
	xmlns:dr3d="urn:oasis:names:tc:opendocument:xmlns:dr3d:1.0"
	xmlns:math="http://www.w3.org/1998/Math/MathML"
	xmlns:form="urn:oasis:names:tc:opendocument:xmlns:form:1.0"
	xmlns:script="urn:oasis:names:tc:opendocument:xmlns:script:1.0"
	xmlns:ooo="http://openoffice.org/2004/office"
	xmlns:ooow="http://openoffice.org/2004/writer"
	xmlns:oooc="http://openoffice.org/2004/calc"
	xmlns:dom="http://www.w3.org/2001/xml-events"
	xmlns:xforms="http://www.w3.org/2002/xforms"
	xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
-->
	<xsl:output method="xml" version="1.0" indent="no" encoding="Latin1" omit-xml-declaration="yes"/>
	<xsl:strip-space elements="*"/>

	<!-- the variable to be used to determine the maximum level of headings,
			 it is defined by the attribute 'head-maxlevel' of the root node if it
			 exists, else it's the default 3 (maximum possible is 5) -->
	<xsl:variable name="maxlevel">
		<xsl:choose>
			<xsl:when test="//map/node/attribute[@NAME='tex-maxlevel']">
				<xsl:value-of select="//map/node/attribute[@NAME='tex-maxlevel']/@VALUE"/>
			</xsl:when>
			<xsl:otherwise><xsl:value-of select="'3'"/></xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

  <!-- Define 'tex-img-float' how images are to be handled 
       if defined but empty: show images inline 
       every other value: used as position attribute in \begin{figure}[htb]'
       if undefined: Default 'htb'
       -->
	<xsl:variable name="imgfloat">
		<xsl:choose>
			<xsl:when test="//map/node/attribute[@NAME='tex-img-float']">
				<xsl:value-of select="//map/node/attribute[@NAME='tex-img-float']/@VALUE"/>
			</xsl:when>
			<xsl:otherwise><xsl:value-of select="'htb'"/></xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

	
	<xsl:template match="map">
	<!-- As to now we do not use anys header  because this is to be included in a latex-book -->
	<xsl:apply-templates mode="heading"/>
	</xsl:template>

<!-- output each node as heading -->
<xsl:template match="node" mode="heading">
<xsl:param name="level" select="0"/>
<xsl:choose> <!-- we change our mind if the NoHeading attribute is present -->
<xsl:when test="attribute/@NAME = 'NoHeading'">
	<xsl:apply-templates select="." />
</xsl:when>
<xsl:when test="$level &gt; $maxlevel"> <!-- Schon zu tief drin -->
	<xsl:apply-templates select="." />
</xsl:when>
<xsl:otherwise>
		<xsl:variable name="command">
		<xsl:choose>
			<xsl:when test="$level='0'">
				<xsl:text>chapter</xsl:text>
			</xsl:when>
			<xsl:when test="$level='1'">
				<xsl:text>section</xsl:text>
			</xsl:when>
			<xsl:when test="$level='2'">
				<xsl:text>subsection</xsl:text>
			</xsl:when>
			<xsl:when test="$level='3'">
				<xsl:text>subsubsection</xsl:text>
			</xsl:when>
			<xsl:when test="$level='4'">
				<xsl:text>paragraph</xsl:text>
			</xsl:when>
			<xsl:when test="$level='3'">
				<xsl:text>subparagraph</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>Standard</xsl:text> <!-- we should never arrive here -->
			</xsl:otherwise>
		</xsl:choose>
		</xsl:variable>

	<xsl:text>
	\</xsl:text><xsl:value-of select="string($command)" /><xsl:text>{</xsl:text>
	<xsl:call-template name="textnode"> <!-- richcontent is not expected! -->
		<xsl:with-param name="style"></xsl:with-param>
	</xsl:call-template>
	<xsl:text>}</xsl:text>
<!-- Label setzen, wenn definiert -->
		<xsl:if test="@ID != ''">
		  <xsl:text>\label{</xsl:text><xsl:value-of select="@ID"/><xsl:text>}</xsl:text>
		</xsl:if>
	<xsl:call-template name="output-notecontent" />
	<xsl:apply-templates select="hook|@LINK"/>

	<!-- if the level is higher than maxlevel, or if the current node is
				 marked with LastHeading, we start outputting normal paragraphs,
	else we loop back into the heading mode -->
	<xsl:choose>
		<xsl:when test="attribute/@NAME = 'LastHeading'">
			<xsl:apply-templates select="node" />
		</xsl:when>
		<xsl:when test="$level &lt; $maxlevel">
			<xsl:apply-templates select="node" mode="heading">
				<xsl:with-param name="level" select="$level + 1"/>
			</xsl:apply-templates>
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates select="node" />
		</xsl:otherwise>
		</xsl:choose>
</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- Output 'normal' nodes -->
	<xsl:template match="node">
		<xsl:variable name="nodesNextLevel" select="count(./node)" /> <!-- Anzahl unterlagerter Knoten feststellen -->
		<xsl:variable name="nodesThisLevel" select="count(../node) - count(../node//img) - count(../node//table)" /> <!-- Anzahl Knoten in diesem Level feststellen -->

		<xsl:variable name="nodeType">
			<xsl:choose>
				<xsl:when test="./richcontent//table">
					<xsl:text>table</xsl:text>
				</xsl:when>
				<xsl:when test="./richcontent//img">
					<xsl:text>img</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:choose>
						<xsl:when test="$nodesThisLevel &gt; '1'">
							<xsl:text>item</xsl:text>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text>text</xsl:text>
						</xsl:otherwise>
				</xsl:choose>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:variable name="id">
			<xsl:if test="@ID != ''">
				<xsl:value-of select="@ID"/>
			</xsl:if>
		</xsl:variable>

		<xsl:variable name="format">
			<xsl:choose>
				<xsl:when test="attribute/@NAME='tex-format'">
					<xsl:value-of select="./attribute[@NAME='tex-format']/@VALUE"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:choose>
						<xsl:when test="$nodeType='img'">
							<xsl:text>scale=0.5</xsl:text>
						</xsl:when>
						<xsl:when test="$nodeType='img'">
							<xsl:text>lllll</xsl:text>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text>noformat</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:variable name="caption">
			<xsl:if test="attribute/@NAME='Caption'">
				<xsl:value-of select="./attribute[@NAME='Caption']/@VALUE"/>
			</xsl:if>
		</xsl:variable>


    <!-- Testausgaben
    <xsl:text>Testausgaben: </xsl:text><xsl:value-of select="$caption"/><xsl:text> - </xsl:text><xsl:value-of select="$format"/>
    -->
		<xsl:if test="$nodesThisLevel &gt; '1' and position()='1'">
			<xsl:text>\begin{itemize}
			</xsl:text>
		</xsl:if>

		<xsl:if test="$nodeType = 'item'">
		<xsl:text>\item </xsl:text>
			<xsl:if test="@ID != ''">
			<xsl:text>\label{</xsl:text><xsl:value-of select="@ID"/><xsl:text>}</xsl:text>
			</xsl:if>
		</xsl:if>

		<xsl:call-template name="output-nodecontent">
		  <xsl:with-param name="id" select="$id" />
		  <xsl:with-param name="format" select="$format" />
		  <xsl:with-param name="caption" select="$caption" />
		</xsl:call-template>
		<xsl:text>\par
		</xsl:text> <!-- Absatz endet immer nach einer node -->


		<xsl:apply-templates select="node"/>
		<xsl:if test="$nodesThisLevel &gt; '1' and position()=last()">
			<xsl:text>
			\end{itemize}
			</xsl:text>
		</xsl:if>

		<xsl:apply-templates select="hook|@LINK"/>
		<xsl:call-template name="output-notecontent" />
		
		<xsl:if test="node/arrowlink">
		<xsl:text>siehe auch \ref{</xsl:text><xsl:value-of select="node/arrowlink/@destination"/><xsl:text>}</xsl:text>
		</xsl:if>
	</xsl:template>

	
	<xsl:template match="hook|@LINK">
		<xsl:text>Siehe auch \url{</xsl:text><xsl:value-of select="."/><xsl:text>}</xsl:text>
	</xsl:template>
	
<!--	<xsl:template match="hook[@NAME='accessories/plugins/NodeNote.properties']">
		<xsl:choose>
			<xsl:when test="./text">
				<text:p text:style-name="Standard">
					<xsl:value-of select="./text"/>
				</text:p>
			</xsl:when>
		</xsl:choose>
	</xsl:template> 
	
	<xsl:template match="node" mode="childoutputOrdered">
		<xsl:param name="nodeText"></xsl:param>
			<text:ordered-list text:style-name="L1"
				text:continue-numbering="true">
				<text:list-item>
					<xsl:apply-templates select=".." mode="childoutputOrdered">
						<xsl:with-param name="nodeText"><xsl:copy-of
								select="$nodeText"/></xsl:with-param>
					</xsl:apply-templates>
				</text:list-item>
			</text:ordered-list>
	</xsl:template>
	
	
	<xsl:template match="map" mode="childoutputOrdered">
		<xsl:param name="nodeText"></xsl:param>
		<xsl:copy-of select="$nodeText"/>
	</xsl:template>
-->
	<xsl:template match="node" mode="depthMesurement">
        <xsl:param name="depth" select=" '0' "/>
        <xsl:apply-templates select=".." mode="depthMesurement">
                <xsl:with-param name="depth" select="$depth + 1"/>
        </xsl:apply-templates>
	</xsl:template>
	<xsl:template match="map" mode="depthMesurement">
        <xsl:param name="depth" select=" '0' "/>
		<xsl:value-of select="$depth"/>
	</xsl:template>

		
	<!-- Give links out. 
	<xsl:template match="@LINK">
			<xsl:element name="text:a" namespace="text">
				<xsl:attribute namespace="xlink" name="xlink:type">simple</xsl:attribute>
				<xsl:attribute namespace="xlink" name="xlink:href">
				        -->
					<!-- Convert relative links, such that they start with "../". 
					     This is due to the fact, that OOo calculates all relative links from the document itself! -->
					     <!--
					<xsl:choose>
						<xsl:when test="starts-with(.,'/') or contains(.,':')"> -->
							<!-- absolute link -->
							<!--
							<xsl:value-of select="."/>
						</xsl:when>
						<xsl:otherwise>
						-->
							<!-- relative link, put "../" at the front -->
							<!--
							<xsl:text>../</xsl:text><xsl:value-of select="."/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:attribute>
				<xsl:value-of select="."/>
			</xsl:element>
	</xsl:template>
	-->

	<xsl:template name="output-nodecontent">
		<xsl:param name="style">Standard</xsl:param>
		<xsl:param name="id" /> <!-- id for label -->
		<xsl:param name="format" /><!-- Formatting -->
		<xsl:param name="caption" /><!-- title line -->
			<xsl:choose>
			<xsl:when test="richcontent[@TYPE='NODE']">
				<xsl:apply-templates select="richcontent[@TYPE='NODE']/html/body" mode="richcontent">
					<xsl:with-param name="style" select="$style"/>
					<xsl:with-param name="id" select="$id"/>
					<xsl:with-param name="format" select="$format"/>
					<xsl:with-param name="caption" select="$caption" />
				</xsl:apply-templates>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="textnode" />
			</xsl:otherwise>
			</xsl:choose>
	</xsl:template> <!-- xsl:template name="output-nodecontent" -->
	
	<xsl:template name="output-notecontent">
		<xsl:if test="richcontent[@TYPE='NOTE']">
			<xsl:apply-templates select="richcontent[@TYPE='NOTE']/html/body" mode="richcontent" >
				<xsl:with-param name="style">Standard</xsl:with-param>					
			</xsl:apply-templates>
		</xsl:if>
	</xsl:template> <!-- xsl:template name="output-note" -->


	<xsl:template name="textnode">
		<!--
		<xsl:variable name="anzahl" select="count(./node)" />  Anzahl unterlagerter Knoten feststellen
		<xsl:variable name="anzahlEigene" select="count(../node)" />  Anzahl Knoten auf eigener Ebene feststellen 
		<xsl:text>(textnode-anfang </xsl:text><xsl:value-of select="$anzahlEigene" /><xsl:text>/</xsl:text><xsl:value-of select="$anzahl" /><xsl:text>)</xsl:text>
		-->
		<xsl:call-template name="format_text">
			<xsl:with-param name="nodetext">
				<xsl:choose>
					<xsl:when test="@TEXT = ''"><xsl:text> </xsl:text></xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="@TEXT" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:with-param>
		</xsl:call-template>
	<!-- <xsl:text>(textnode-ende)</xsl:text> -->
	</xsl:template> <!-- xsl:template name="textnode" -->
	

	<!-- replace ASCII line breaks through ODF line breaks (br) -->
	<xsl:template name="format_text">
		<xsl:param name="nodetext"></xsl:param>
		<xsl:if test="string-length(substring-after($nodetext,'&#xa;')) = 0">
			<xsl:value-of select="$nodetext" />
		</xsl:if>
		<xsl:if test="string-length(substring-after($nodetext,'&#xa;')) > 0">
			<xsl:value-of select="substring-before($nodetext,'&#xa;')" />
			<xsl:text>\par{}</xsl:text>
			<xsl:call-template name="format_text">
				<xsl:with-param name="nodetext">
					<xsl:value-of select="substring-after($nodetext,'&#xa;')" />
				</xsl:with-param>
			</xsl:call-template>
		</xsl:if>
	</xsl:template> <!-- xsl:template name="format_text" -->

	<xsl:template match="body" mode="richcontent">
		<xsl:param name="id" /> <!-- id for label -->
		<xsl:param name="format" /><!-- Formatting -->
		<xsl:param name="caption" /><!-- Title -->
		<xsl:param name="style">Standard</xsl:param>
<!--       <xsl:copy-of select="string(.)"/> -->
		<xsl:apply-templates select="text()|*" mode="richcontent">
			<xsl:with-param name="style" select="$style"></xsl:with-param>
			<xsl:with-param name="id" select="$id" />
			<xsl:with-param name="format" select="$format" />
			<xsl:with-param name="caption" select="$caption" />
		</xsl:apply-templates>
	</xsl:template> 

	<xsl:template match="text()" mode="richcontent">	<xsl:copy-of select="string(.)"/></xsl:template> 

	<xsl:template match="table" mode="richcontent">
		<xsl:param name="id" /> <!-- id for label -->
		<xsl:param name="format" /><!-- Formatting -->
		<xsl:text>\par\begin{tabular}{</xsl:text>
<!--		<xsl:value-of select="tr/count(td)" /> -->
		<xsl:choose>
			<xsl:when test="$format=''">
				<xsl:text>|llllll|}\hline
				</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$format" />
				<xsl:text>}\hline
				</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:apply-templates mode="richcontent" />
		<xsl:text>\end{tabular}
		</xsl:text>
	</xsl:template> 


	<xsl:template match="tr" mode="richcontent">
		<xsl:apply-templates mode="richcontent" />
		<xsl:text>\\ \hline
		</xsl:text> <!-- Record separator -->
	</xsl:template> 


	<xsl:template match="td" mode="richcontent">
		<xsl:apply-templates select="text()|*" mode="richcontent" >
		</xsl:apply-templates>
<!--		<xsl:value-of  disable-output-escaping="yes" select="." /> -->
		<xsl:if test="position() != last()"><xsl:text disable-output-escaping="yes">&#38;</xsl:text></xsl:if> <!-- Field separator -->
	</xsl:template> 



	<xsl:template match="br" mode="richcontent">
		<xsl:text>\par{}</xsl:text>
	</xsl:template> 

	<xsl:template match="b" mode="richcontent">
		<xsl:text>{\bf </xsl:text><xsl:value-of select="." /><xsl:text>}</xsl:text>
	</xsl:template> 

	<xsl:template match="i" mode="richcontent">
		<xsl:text>\emph{</xsl:text><xsl:value-of select="." /><xsl:text>}</xsl:text>
	</xsl:template> 

	<xsl:template match="img" mode="richcontent">
		<xsl:param name="id"/> <!-- id for label -->
		<xsl:param name="format" select="'scale=0.5'"/><!-- Formatting -->
		<xsl:param name="caption" select="'No Caption defined in Mindmap'"/><!-- Formatting -->
	  <xsl:if test="$imgfloat=''" >
			<xsl:text>\includegraphics[scale=0.5]{</xsl:text><xsl:value-of select="./@src"/><xsl:text>}</xsl:text>
		</xsl:if>
	  <xsl:if test="$imgfloat!=''" >
			<xsl:text>
			\begin{figure}[</xsl:text><xsl:value-of select="$imgfloat"/><xsl:text>]
			\includegraphics[</xsl:text><xsl:value-of select="$format"/><xsl:text>]{</xsl:text><xsl:value-of select="./@src"/><xsl:text>}
			\caption{</xsl:text><xsl:value-of select="$caption"/><xsl:text>}
			\label{</xsl:text><xsl:value-of select="$id"/><xsl:text>}
			\end{figure}
			</xsl:text>
		</xsl:if>
	</xsl:template> 

	<xsl:template match="u" mode="richcontent">
		<xsl:text>\underline{</xsl:text><xsl:value-of select="." /><xsl:text>}</xsl:text>
	</xsl:template> 
	<xsl:template match="ul" mode="richcontent">
		<xsl:param name="style">Standard</xsl:param>
		<xsl:text>\begin{itemize}
		</xsl:text>
			<xsl:apply-templates select="text()|*" mode="richcontentul"><xsl:with-param name="style" select="$style"></xsl:with-param></xsl:apply-templates>
		<xsl:text>\end{itemize}
		</xsl:text>
	</xsl:template> 

	<xsl:template match="ol" mode="richcontent">
		<xsl:param name="style">Standard</xsl:param>
		<xsl:text>\begin{enumerate}
		</xsl:text>
			<xsl:apply-templates select="text()|*" mode="richcontentol"><xsl:with-param name="style" select="$style"></xsl:with-param></xsl:apply-templates>
		<xsl:text>\end{enumerate}
		</xsl:text>
	</xsl:template> 

	<xsl:template match="li" mode="richcontentul">
		<xsl:text>\item </xsl:text><xsl:value-of select="@text" /><xsl:text>
		</xsl:text>
	</xsl:template> 
	<xsl:template match="li" mode="richcontentol">
		<xsl:text>\item </xsl:text><xsl:value-of select="@text" /><xsl:text>
		</xsl:text>
	</xsl:template> 


<!-- Zu kompliziert
	<xsl:template match="li" mode="richcontentul">
		<xsl:param name="style">Standard</xsl:param>
      <text:list-item>
        <text:p text:style-name="P1">
			<xsl:apply-templates select="text()|*" mode="richcontent"><xsl:with-param name="style" select="$style"></xsl:with-param></xsl:apply-templates>
		</text:p>
      </text:list-item>
	</xsl:template> 
	<xsl:template match="li" mode="richcontentol">
		<xsl:param name="style">Standard</xsl:param>
	    <text:list-item>
        <text:p text:style-name="P2">
			<xsl:apply-templates select="text()|*" mode="richcontent"><xsl:with-param name="style" select="$style"></xsl:with-param></xsl:apply-templates>			
		</text:p>
      </text:list-item>
	</xsl:template> 
-->	
	<!-- Table: 
		    <table:table table:name="Table1" table:style-name="Table1">
      <table:table-column table:style-name="Table1.A" table:number-columns-repeated="3"/>
      <table:table-row>
        <table:table-cell table:style-name="Table1.A1" table:value-type="string">
          <text:p text:style-name="Table Contents">T11</text:p>
        </table:table-cell>
        <table:table-cell table:style-name="Table1.A1" table:value-type="string">
          <text:p text:style-name="Table Contents">T21</text:p>
        </table:table-cell>
        <table:table-cell table:style-name="Table1.C1" table:value-type="string">
          <text:p text:style-name="Table Contents">T31</text:p>
        </table:table-cell>
      </table:table-row>
      <table:table-row>
        <table:table-cell table:style-name="Table1.A2" table:value-type="string">
          <text:p text:style-name="Table Contents">T12</text:p>
        </table:table-cell>
        <table:table-cell table:style-name="Table1.A2" table:value-type="string">
          <text:p text:style-name="Table Contents">T22</text:p>
        </table:table-cell>
        <table:table-cell table:style-name="Table1.C2" table:value-type="string">
          <text:p text:style-name="Table Contents">T32</text:p>
        </table:table-cell>
      </table:table-row>
      <table:table-row>
        <table:table-cell table:style-name="Table1.A2" table:value-type="string">
          <text:p text:style-name="Table Contents">T13</text:p>
        </table:table-cell>
        <table:table-cell table:style-name="Table1.A2" table:value-type="string">
          <text:p text:style-name="Table Contents">T23</text:p>
        </table:table-cell>
        <table:table-cell table:style-name="Table1.C2" table:value-type="string">
          <text:p text:style-name="Table Contents">T32</text:p>
        </table:table-cell>
      </table:table-row>
    </table:table>
-->
	
	
</xsl:stylesheet>
