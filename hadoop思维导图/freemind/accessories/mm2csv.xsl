<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:output method="text" encoding="UTF-8"/>
 <xsl:template match="/">
 <xsl:apply-templates/>
 </xsl:template>
  <xsl:template name="linebreak">
   <xsl:text> 
</xsl:text>
  </xsl:template>
  <xsl:template match="map">
   <xsl:apply-templates select="child::node"/>
  </xsl:template>
  <xsl:template match="node">
   <xsl:param name="commaCount">0</xsl:param>
    <xsl:if test="$commaCount > 0">
      <xsl:call-template name="writeCommas">
       <xsl:with-param name="commaCount" select="$commaCount"/>
      </xsl:call-template>
    </xsl:if>
    <xsl:value-of select="@TEXT"/>
     <xsl:call-template name="linebreak"/>
      <xsl:apply-templates select="child::node">
        <xsl:with-param name="commaCount" select="$commaCount + 1"/>
      </xsl:apply-templates>
     </xsl:template>
     <xsl:template name="writeCommas">
      <xsl:param name="commaCount">0</xsl:param>
       <xsl:if test="$commaCount > 0">,<xsl:call-template name="writeCommas">
         <xsl:with-param name="commaCount" select="$commaCount - 1"/>
     </xsl:call-template>
    </xsl:if>
 </xsl:template>
</xsl:stylesheet>

 	  	 
