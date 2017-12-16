/*FreeMind - A Program for creating and viewing Mindmaps
 *Copyright (C) 2000-2011  Joerg Mueller, Daniel Polansky, Christian Foltin and others.
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
 * Created on 22.06.2011
 */

/** 
 * To use this script, call 
 * java -Dstartup_groovy_script=accessories\ExportToPdf.groovy -jar lib\freemind.jar
 */

// show map (otherwise it can't be exported)
c.showThisMap()
// get exporter
def ex = c.getHookFactory().createModeControllerHook("plugins/ExportPdf.xml")
ex.setController(c)
// calculate file name
def file = c.getMap().getFile().getAbsolutePath().replaceFirst(
                    "\\.[^.]*?\$", "") + ".pdf"
try {
	ex.exportAsPdf(false, c.getRootNode(),
			new File(file));
	} catch(e) { print e; }
System.exit(0);
return 0;
