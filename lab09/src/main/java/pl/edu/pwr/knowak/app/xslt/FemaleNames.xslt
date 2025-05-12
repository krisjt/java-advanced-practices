<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h2>Female Names</h2>
                <table border="1">
                <tr bgcolor="#9acd32">
                    <th style="text-align:centre">Name</th>
                    <th style="text-align:centre">Count</th>
                </tr>
                <ul>
                    <xsl:for-each select="response/row/row[gndr='FEMALE']">
                        <tr>
                            <td><xsl:value-of select="nm"/></td>
                            <td><xsl:value-of select="cnt"/></td>
                        </tr>
                    </xsl:for-each>
                </ul>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
