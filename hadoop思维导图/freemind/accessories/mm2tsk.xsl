<?xml version="1.0" encoding="UTF-8"?>
<!--
	 
Document   : mm2tsk.xsl
Created on : 02 October 2010 - 22:14
Author     : Giacomo Lacava toyg@users at sourceforge.net
Description: transforms freemind mm format to tsk, used by TaskCoach.

Note: this doesn't handle richtext nodes yet

* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

Patches item #3080120, was opened at 2010-10-02 21:34
https://sourceforge.net/tracker/?func=detail&atid=307118&aid=3080120&group_id=7118
Summary: Export to TaskCoach

Initial Comment:
The attached XSL will convert a MM file to TSK, the format used by TaskCoach ( http://www.taskcoach.org ), a popular todo manager (FOSS).

At the moment it doesn't export richtext nodes properly  -- surely you already have a good way to "flatten" them, but I can't find it. TaskCoach doesn't handle HTML, afaik.

TSK files require a date-time value in the "startdate" attribute of tasks; it has to be set in the past for nodes to be seen as "active". I don't know if your XSL parser handles XSLT 2.0 (many don't), so I used a 1.0 extension available online in order to do that -- I add the current timestamp, and since more than a second will always pass between the file being saved and the export being opened in TaskCoach, it seems to work.

Let me know if you need anything else.
    
-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:datetime="http://exslt.org/dates-and-times" exclude-result-prefixes="datetime"> 

	<xsl:param name="datestr" select="datetime:dateTime()" />
	<xsl:param name="date">
		<xsl:value-of select="substring($datestr,0,5)" />
		<xsl:text>-</xsl:text>
		<xsl:value-of select="substring($datestr,6,2)" />
		<xsl:text>-</xsl:text>
		<xsl:value-of select="substring($datestr,9,2)" />
		<xsl:text> </xsl:text>
		<xsl:value-of select="substring($datestr,12,2)" />
		<xsl:text>:</xsl:text>
		<xsl:value-of select="substring($datestr,15,2)" />
		<xsl:text>:</xsl:text>
		<xsl:value-of select="substring($datestr,18,2)" />
	</xsl:param>

	<xsl:template match="/">
		<xsl:processing-instruction name="taskcoach">release="1.1.4" tskversion="30"</xsl:processing-instruction>
		<tasks>
			<xsl:apply-templates />
		</tasks>
	</xsl:template>

	<xsl:template match="node">
		<task>
			<xsl:attribute name="startdate">
				<xsl:value-of select="$date"/>	
			</xsl:attribute>
			<xsl:attribute name="status">1</xsl:attribute>
			<xsl:attribute name="subject">
				<xsl:value-of select="@TEXT"/>
			</xsl:attribute>
			<xsl:apply-templates />
		</task>
	</xsl:template>

</xsl:stylesheet>
