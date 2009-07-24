<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:stel="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd"
	xmlns:sb-xsd="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd">

	<xsl:import href="xsl/copy.xsl" />

	<xsl:output method="xml" version="1.0" encoding="UTF-8"
		indent="yes" />

	<xsl:template match="sb-xsd:*">
		<xsl:element name="stel:{local-name()}" namespace="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd">
			<xsl:apply-templates />
		</xsl:element>
	</xsl:template>

</xsl:stylesheet>