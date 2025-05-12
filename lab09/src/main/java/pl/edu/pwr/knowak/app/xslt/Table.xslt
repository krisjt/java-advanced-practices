<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h2>Baby Names</h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th style="text-align:centre">Name</th>
                        <th style="text-align:centre">Gender</th>
                        <th style="text-align:centre">Year</th>
                        <th style="text-align:centre">Ethnicity</th>
                        <th style="text-align:centre">Count</th>
                        <th style="text-align:centre">Rank</th>
                    </tr>

                    <xsl:for-each select="response/row/row">
                        <tr>
                            <td><xsl:value-of select="nm"/></td>
                            <td><xsl:value-of select="gndr"/></td>
                            <td><xsl:value-of select="brth_yr"/></td>
                            <td><xsl:value-of select="ethcty"/></td>
                            <td><xsl:value-of select="cnt"/></td>
                            <td><xsl:value-of select="rnk"/></td>
                        </tr>
                    </xsl:for-each>

                    <!-- Wiersz z sumą -->
                    <tr bgcolor="#dddddd">
                        <td colspan="4" style="text-align:right"><strong>Total Count:</strong></td>
                        <td colspan="2">
                            <xsl:value-of select="sum(response/row/row/cnt)"/>
                        </td>
                    </tr>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
