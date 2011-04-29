<?xml version="1.0" encoding="UTF-8"?>
<!--
 * Copyright (c) 2009-2011 Gemeente Rotterdam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the European Union Public Licence (EUPL),
 * version 1.1 (or any later version).
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * European Union Public Licence for more details.
 *
 * You should have received a copy of the European Union Public Licence
 * along with this program. If not, see
 * http://www.osor.eu/eupl/european-union-public-licence-eupl-v.1.1
-->
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