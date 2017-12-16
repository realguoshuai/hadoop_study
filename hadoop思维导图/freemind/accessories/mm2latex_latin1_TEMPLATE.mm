<map version="0.9.0">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1216974513042" ID="ID_833600903" MODIFIED="1238925165356" TEXT="Example of map exportable to LaTex">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      The root node is exported as document title (with format 'chapter').
    </p>
    <p>
      Attributes of the root node are used as global document properties if they have the prefix &quot;tex-&quot; in their name. Acceptable names are maxlevel and img-float.
    </p>
    <p>
      The attribute &quot;tex-maxlevel&quot; is used to define the maximum of nodes until which 'Heading' styles are used. If the attribute is not defined, the default value is 3. The maximum possible is 5 (section, subsection, subsubsection, paragraph,subparagraph.
    </p>
  </body>
</html>
</richcontent>
<attribute_layout NAME_WIDTH="91" VALUE_WIDTH="91"/>
<attribute NAME="tex-maxlevel" VALUE="3"/>
<attribute NAME="tex-img-float" VALUE="htb"/>
<node CREATED="1238918404359" ID="ID_1934280567" MODIFIED="1238918407890" POSITION="right" TEXT="Introduction">
<node CREATED="1238917785012" ID="ID_1920085495" MODIFIED="1238918735994" TEXT="Purpose">
<attribute NAME="LastHeading" VALUE=""/>
<node CREATED="1238917797246" ID="ID_112127867" MODIFIED="1238920553674" TEXT="I often use freemind to make up my mind about how to tackle a certain task. When I&apos;m done thinking I want to document my results. For this I use HTML, Word or LaTeX. Obviously I&apos;d like to see images and all the formatting I have done"/>
<node CREATED="1238917902916" ID="ID_1286589065" MODIFIED="1238918298236" TEXT="For HTML and Word I found some nice Templates, LaTeX though lacked the images and formatted text. Fortunalety the authors of  the template for word (Naoki Nose, 2006, and Eric Lavarde, 2008) did I very good job in analyzing the XML-Data."/>
<node CREATED="1238918299470" ID="ID_286082551" MODIFIED="1238920599314" TEXT="I decided to stick to their general setup and used some of their logic, especially for the attributes. This should help all those people, who want to use several output channels at the same time."/>
</node>
<node CREATED="1238918413109" ID="ID_1545289664" MODIFIED="1238918450202" TEXT="How to create a LaTex-File">
<node CREATED="1238918472358" ID="ID_1433056762" MODIFIED="1238919043256" TEXT="Rule 1: Creating the Mindmap">
<node CREATED="1238918487404" ID="ID_172643764" MODIFIED="1238918528341" TEXT="Do not think about LaTeX when setting up your Mindmap, think about the problem you want to solve or the subject you want to write about"/>
<node CREATED="1238918850055" ID="ID_564855825" MODIFIED="1238918876961" TEXT="You may use the character set latin1 (Germans: Umlaute sind m&#xf6;glich!)"/>
</node>
<node CREATED="1238918536638" ID="ID_763391417" MODIFIED="1238919058100" TEXT="Rule 2: Creating the Environment">
<node CREATED="1238918615480" ID="ID_1647968502" MODIFIED="1238918682245" TEXT="Create a Masterfile with LaTeX-Code like Header, pagestyle etc"/>
<node CREATED="1238918683479" ID="ID_1758774155" MODIFIED="1238918801744" TEXT="Add an &apos;input&apos; or &apos;include&apos;-command at the spot, where you want your mindmap to appear."/>
</node>
<node CREATED="1238919017053" ID="ID_405215367" MODIFIED="1238919072287" TEXT="Rule 3: Exporting the Mindmap">
<node CREATED="1238918804228" ID="ID_1695632461" MODIFIED="1238919010319" TEXT="Export your mindmap via XSL-Export using this XSL-Stylesheet (&apos;mm2latex.xsl&apos;)"/>
</node>
<node CREATED="1238919085084" ID="ID_294996980" MODIFIED="1238919504375" TEXT="Rule 4: Finishing Touches">
<node CREATED="1238919129224" ID="ID_169878621" MODIFIED="1238919245410" TEXT="Compile your masterfile, there may be some TeX-related errors, that you may have to take care of. Usually there a these groups">
<node CREATED="1238919249613" ID="ID_1347745562" MODIFIED="1238919321018" TEXT="Mask any special character that is not allowed in LaTeX (Ampersand, Dollar, Percent)"/>
<node CREATED="1238919321706" ID="ID_766320487" MODIFIED="1238919482282" TEXT="Use the attribute &apos;Noheading&apos; if a single node is not supposed to be a section, subsection or subsubsection">
<arrowlink DESTINATION="ID_843043724" ENDARROW="Default" ENDINCLINATION="677;0;" ID="Arrow_ID_1401678800" STARTARROW="None" STARTINCLINATION="677;0;"/>
</node>
<node CREATED="1238919386408" ID="ID_1409718510" MODIFIED="1238919485250" TEXT="Use the attribute &apos;LastHeading&apos; if all nodes below this one are meant to be text (Look a the nodes &apos;Purpose&apos; and &apos;How to ..&apos; in this section)">
<arrowlink DESTINATION="ID_1342553402" ENDARROW="Default" ENDINCLINATION="933;0;" ID="Arrow_ID_554920267" STARTARROW="None" STARTINCLINATION="933;0;"/>
</node>
</node>
<node CREATED="1238919516703" ID="ID_1251654569" MODIFIED="1238919561093" TEXT="For your images you may want to define the size/format or a Caption">
<arrowlink DESTINATION="ID_798981776" ENDARROW="Default" ENDINCLINATION="255;0;" ID="Arrow_ID_1788752982" STARTARROW="None" STARTINCLINATION="255;0;"/>
</node>
</node>
</node>
</node>
<node CREATED="1216974528086" ID="ID_1996762094" MODIFIED="1216974692827" POSITION="right" TEXT="Chapter 1">
<node CREATED="1216974536680" ID="ID_418841879" MODIFIED="1216974708501" TEXT="Chapter 1.1">
<node CREATED="1216974544352" ID="ID_1231871458" MODIFIED="1238926367864" TEXT="Chapter 1.1.1">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      This is a note belonging to Chapter 1.1.1, such notes are exported with style &quot;Body Text&quot; but any formatting,
    </p>
    <p>
      or even new lines are lost. That's sad but that's reality.
    </p>
  </body>
</html>
</richcontent>
<node CREATED="1216974561800" ID="ID_35441158" MODIFIED="1216974730363" TEXT="Chapter 1.1.1.1">
<node CREATED="1216974620653" ID="ID_1657992058" MODIFIED="1216991329486" TEXT="Text wich is"/>
<node CREATED="1216974660607" ID="ID_1076025767" MODIFIED="1216991352258" TEXT="deeper than the"/>
<node CREATED="1216974664012" ID="ID_1612257345" MODIFIED="1216991345298" TEXT="header-maxlevel attribute"/>
<node CREATED="1216974667197" ID="ID_1877504467" MODIFIED="1216991366458" TEXT="is exported with &quot;Normal&quot; style."/>
</node>
<node CREATED="1216974674739" ID="ID_843043724" MODIFIED="1238919482282" TEXT="This nodes will be exported as a normal paragraph">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      By marking a node with the attribute 'NoHeading' (the value is not important), you make sure that this chapter will be exported as normal paragraph, together with all nodes below.
    </p>
  </body>
</html></richcontent>
<attribute_layout NAME_WIDTH="62" VALUE_WIDTH="91"/>
<attribute NAME="NoHeading" VALUE=""/>
<node CREATED="1217604758817" ID="ID_863632446" MODIFIED="1217604766680" TEXT="Like also this one"/>
</node>
</node>
</node>
<node CREATED="1216974696283" ID="ID_1342553402" MODIFIED="1238926368958" TEXT="Chapter 1.2 - mark a header as last heading">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      By marking a node with the attribute 'LastHeading' (the value is not important), you make sure that this chapter will be exported as the last heading in the hierarchy, i.e. all nodes below the chapter will be exported as normal paragraphs.
    </p>
  </body>
</html>
</richcontent>
<attribute_layout NAME_WIDTH="69" VALUE_WIDTH="91"/>
<attribute NAME="LastHeading" VALUE=""/>
<node CREATED="1217603132140" ID="ID_1323406791" MODIFIED="1217603515832" TEXT="this node becomes a normal paragraph&#xa;even though it&apos;s above the defaultlevel">
<node CREATED="1217603804767" ID="ID_630190221" MODIFIED="1217603812619" TEXT="And this one as well"/>
</node>
<node CREATED="1217603814001" ID="ID_1067471434" MODIFIED="1217603819328" TEXT="And also this one"/>
</node>
</node>
<node CREATED="1216991067197" ID="ID_334419387" MODIFIED="1238756143265" POSITION="right" TEXT="Chapter 2: richcontent nodes">
<attribute NAME="LastHeading" VALUE=""/>
<node CREATED="1238756415812" ID="ID_983007227" MODIFIED="1238756417593" TEXT="Images">
<node CREATED="1238741133718" ID="ID_1878632215" MODIFIED="1238741179593" TEXT="Images are exported depending on the value of tex-img-float either as inline or as float-value">
<node CREATED="1238741848593" ID="ID_798981776" MODIFIED="1238919609311">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <img src="show.png" />
  </body>
</html></richcontent>
<attribute NAME="Caption" VALUE="Title of Image"/>
<attribute NAME="tex-format" VALUE="scale=0.7"/>
</node>
</node>
<node CREATED="1238756387328" ID="ID_379489595" MODIFIED="1238756429093" TEXT="There a several attributes which can be used with Images:">
<node CREATED="1238756457906" ID="ID_1347482209" MODIFIED="1238756460984" TEXT="Caption">
<node CREATED="1238756492640" ID="ID_502354102" MODIFIED="1238756535453" TEXT="the content of this attribute will be used as caption of the image (in float-mode)"/>
</node>
<node CREATED="1238756537718" ID="ID_596764817" MODIFIED="1238756541531" TEXT="tex-format">
<node CREATED="1238756543859" ID="ID_536843546" MODIFIED="1238922177485" TEXT="the content of this attribute is used as parameter of options-part of includegraphics[option]{image}"/>
</node>
</node>
<node CREATED="1238756989984" ID="ID_346917431" MODIFIED="1238757024093" TEXT="References to images are in the same form that you used in your Mindmap."/>
</node>
<node CREATED="1238741185046" ID="ID_616977104" MODIFIED="1238741203187" TEXT="text in Nodes can be formatted"/>
<node CREATED="1238741259703" ID="ID_41096153" MODIFIED="1238741343531">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Tables look like this
    </p>
  </body>
</html></richcontent>
<node CREATED="1238741346375" ID="ID_750910605" MODIFIED="1238926381364">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <table border="0" style="border-left-width: 0; border-top-width: 0; border-right-width: 0; width: 80%; border-bottom-width: 0; border-style: solid">
      <tr>
        <td valign="top" style="border-left-width: 1; border-right-width: 1; border-top-width: 1; width: 33%; border-bottom-width: 1; border-style: solid">
          <p style="margin-left: 1; margin-bottom: 1; margin-right: 1; margin-top: 1">
            <font size="5">Head 1</font>
          </p>
        </td>
        <td valign="top" style="border-left-width: 1; border-right-width: 1; border-top-width: 1; width: 33%; border-bottom-width: 1; border-style: solid">
          <p style="margin-left: 1; margin-bottom: 1; margin-right: 1; margin-top: 1">
            <font size="5">Head 2</font>
          </p>
        </td>
        <td valign="top" style="border-left-width: 1; border-right-width: 1; border-top-width: 1; width: 33%; border-bottom-width: 1; border-style: solid">
          <p style="margin-left: 1; margin-bottom: 1; margin-right: 1; margin-top: 1">
            <font size="5">Head 3</font>
          </p>
        </td>
      </tr>
      <tr>
        <td valign="top" style="border-left-width: 1; border-right-width: 1; border-top-width: 1; width: 33%; border-bottom-width: 1; border-style: solid">
          <p style="margin-left: 1; margin-bottom: 1; margin-right: 1; margin-top: 1">
            data 1 contains <b>bold,</b>&#160;which will&#160;&#160;be shown
          </p>
        </td>
        <td valign="top" style="border-left-width: 1; border-right-width: 1; border-top-width: 1; width: 33%; border-bottom-width: 1; border-style: solid">
          <p style="margin-left: 1; margin-bottom: 1; margin-right: 1; margin-top: 1">
            data 2 contains <i>italics</i>, which will&#160;&#160;be shown
          </p>
        </td>
        <td valign="top" style="border-left-width: 1; border-right-width: 1; border-top-width: 1; width: 33%; border-bottom-width: 1; border-style: solid">
          <p style="margin-left: 1; margin-bottom: 1; margin-right: 1; margin-top: 1">
            data 3 contains <u>underscore</u>&#160;and so on, which will&#160;&#160;be shown (yet)
          </p>
        </td>
      </tr>
      <tr>
        <td valign="top" style="border-left-width: 1; border-right-width: 1; border-top-width: 1; width: 33%; border-bottom-width: 1; border-style: solid">
          <p style="margin-left: 1; margin-bottom: 1; margin-right: 1; margin-top: 1">
            more text
          </p>
        </td>
        <td valign="top" style="border-left-width: 1; border-right-width: 1; border-top-width: 1; width: 33%; border-bottom-width: 1; border-style: solid">
          <p style="margin-left: 1; margin-bottom: 1; margin-right: 1; margin-top: 1">
            more text
          </p>
        </td>
        <td valign="top" style="border-left-width: 1; border-right-width: 1; border-top-width: 1; width: 33%; border-bottom-width: 1; border-style: solid">
          <p style="margin-left: 1; margin-bottom: 1; margin-right: 1; margin-top: 1">
            more text
          </p>
        </td>
      </tr>
    </table>
  </body>
</html>
</richcontent>
<attribute_layout VALUE_WIDTH="142"/>
<attribute NAME="tex-format" VALUE="|p{3cm}|p{3cm}|p{3cm}|"/>
</node>
</node>
</node>
<node CREATED="1216809914482" ID="ID_1308741003" MODIFIED="1238922344578" POSITION="right" TEXT="Chapter 3 - how to export a mindmap to latex ?">
<attribute NAME="LastHeading" VALUE=""/>
<node CREATED="1216809917636" ID="ID_199484608" MODIFIED="1216991907919" TEXT="Chapter 3.1 - create a map following the notes and hints expressed in this example map"/>
<node CREATED="1216809921221" ID="ID_1681718272" MODIFIED="1238922302250" TEXT="Chapter 3.2 - export the map using the File $\leftarrow$ Export $\rightarrow$ Using XSLT... menu">
<node CREATED="1216826868748" ID="ID_1660904657" MODIFIED="1238922383046" TEXT="Chapter 3.2.1 - select the mm2latex.XSL file from the accessories directory in the FreeMind base directory."/>
<node CREATED="1216826924521" ID="ID_1561412985" MODIFIED="1238756702703" TEXT="Chapter 3.2.2 - export to a file with a name ending in .tex"/>
</node>
<node CREATED="1216826940554" ID="ID_769680777" MODIFIED="1238756755734" TEXT="Chapter 3.3 - use latex">
<node CREATED="1238756791781" ID="ID_28353139" MODIFIED="1238922464093">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      The file produced by this XSL-Sheet <b>does not contain</b>&#160;any&#160;Header information. it is intended as a part which can be included into an existing TeX master file.
    </p>
    <p>
      You can either use input{filename.tex} to pull it into tex or use include{filename}
    </p>
  </body>
</html>
</richcontent>
</node>
</node>
<node CREATED="1216827072099" ID="ID_785390572" MODIFIED="1216991949417" TEXT="Chapter 3.4 - you&apos;re done, enjoy!"/>
</node>
<node CREATED="1216991668227" ID="ID_1657343694" MODIFIED="1238757042812" POSITION="right" TEXT="Best Practices">
<node CREATED="1238757044593" ID="ID_1921364396" MODIFIED="1238757051203" TEXT="Filesystem">
<node CREATED="1238757053078" ID="ID_1576776962" MODIFIED="1238757069531" TEXT="Think about how you organize your files.">
<node CREATED="1238757071406" ID="ID_135148380" MODIFIED="1238757107359" TEXT="I have one main directory &apos;mindmaps&apos;, which contains the following data">
<node CREATED="1238757109109" ID="ID_1235469122" MODIFIED="1238757115875" TEXT="All Mindmaps"/>
<node CREATED="1238757116484" ID="ID_1972117067" MODIFIED="1238757144015" TEXT="A subdirectory &apos;pics&apos;, which contains all the images I use"/>
<node CREATED="1238920675891" ID="ID_1661088963" MODIFIED="1238920704235" TEXT="a subdirectory &apos;icons&apos; which contains the icons freemind uses"/>
<node CREATED="1238757144546" ID="ID_1358898242" MODIFIED="1238920672126" TEXT="a subdirectory tex, which contains the created Tex-Files"/>
<node CREATED="1238920709453" ID="ID_1948667697" MODIFIED="1238920742969" TEXT="The Master tex file(s)"/>
</node>
<node CREATED="1238920749734" ID="ID_985841966" MODIFIED="1238920849577" TEXT="This setup allows me to use some XML-Tools (like saxon) to parse the directory and  update a tex-File, when the correspondig mindmap has been changed"/>
</node>
</node>
<node CREATED="1238920859373" ID="ID_527613947" MODIFIED="1238920864436" TEXT="More to come ..."/>
</node>
</node>
</map>
