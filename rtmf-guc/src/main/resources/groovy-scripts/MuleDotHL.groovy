#!/home/akoelewijn/programs/groovy-1.6.4/bin/groovy
/*
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
*/

def muleFilename = args[0]
def dotFilename = args[1]

def HTTP_NS_URI="http://www.mulesource.org/schema/mule/http/2.2"
def FILE_NS_URI="http://www.mulesource.org/schema/mule/file/2.2"
def VM_NS_URI="http://www.mulesource.org/schema/mule/vm/2.2"

static nsMap = [ http:HTTP_NS_URI, file:FILE_NS_URI, vm:VM_NS_URI ]

def serviceNode(service){
	outRouterName = service.outbound.'*'[0].name()
	return "${getServiceNodeName(service)} [shape=record,label=\"${service.@name} | ${outRouterName}\"]"
}
def getServiceNodeName(service){
	return nameMaker("service_${service.@name}")
}
def getOutboundService(outbound){
  return outbound.parent().parent().parent()
}
def getOutboundTargetService(outbound,model){
  def s
  def ns = outbound.namespaceURI()
  def nsprefix = getNsPrefix(ns)
  def path = outbound.@path
  model.depthFirst().collect { it ->
    if ( it.name() == 'inbound-endpoint' && it.@path == path && it.namespaceURI() == ns ){
      s = it.parent().parent()
    }
  }
  return s
}
def getNsPrefix(namespaceUri){
	def HTTP_NS_URI="http://www.mulesource.org/schema/mule/http/2.2"
	def FILE_NS_URI="http://www.mulesource.org/schema/mule/file/2.2"
	def VM_NS_URI="http://www.mulesource.org/schema/mule/vm/2.2"
	def ns = [ http:HTTP_NS_URI, file:FILE_NS_URI, vm:VM_NS_URI ]
	if (ns.containsValue(namespaceUri)) {
	    return ns.find{it.value==namespaceUri}.key
	} else {
		return "xxx"
	}
}
def getExternalHttpServiceName(outbound){ 
  name = nameMaker("http://" + outbound.@host.toString() +
		":" + outbound.@port.toString() + "/" + outbound.@path.toString())
  return name
}
def getExternalSmtpServiceName(outbound){ 
	  name = nameMaker("smtp://" + outbound.@host.toString())
	  return name
	}
def externalHttpEndpointNode(outbound){
	return "${getExternalHttpServiceName(outbound)} [shape=record,label=\"${httpEndpointUri(outbound)}\"]"
}
def externalHSmtpEndpointNode(outbound){
	return "${getExternalSmtpServiceName(outbound)} [shape=record,label=\"${smtpEndpointUri(outbound)}\"]"
}
def httpEndpointUri(endpoint){
	  return htmlEncode( "http://" + endpoint.@host.toString() + ":" + endpoint.@port.toString() + "/" + endpoint.@path.toString())
}
def smtpEndpointUri(endpoint){
	  return htmlEncode( "smtp://" + endpoint.@host.toString() + ":" + endpoint.@port.toString() )
}

def nameMaker(name){
  return name.toString().replace("\${","").replace("}","_").replace(".","_").replace("/","_").replace("-","_").replace(":","_")
}
def htmlEncode(path){
	  return path.toString().replace("\$","&#36;").replace("{","&#123;").replace("}","&#125;")
}
def prnt(file,line){
	file << (line + '\n')
}

def connections = []
def externalEndpoints = []
def muleConfig = new XmlSlurper().parse(muleFilename)
def dotFile = new File(dotFilename)
if ( dotFile.exists()){
	dotFile.delete()
}
muleConfig.declareNamespace(nsMap)

prnt dotFile, "digraph G {"
prnt dotFile, " label=\"${muleConfig.model.@name}\""
prnt dotFile, " center=true"
prnt dotFile, " rankdir=\"LR\""
prnt dotFile, " nodesep=2"
prnt dotFile, " sep=2"
prnt dotFile, " graph [fontname=\"Arial\", fontsize = 24, label = \"rtmfguc\" ]";
prnt dotFile, " node [fontname=\"Arial\", fontsize = 16 ]";
muleConfig.model.service.each { service ->
  prnt dotFile, serviceNode(service)
  service.outbound.'*'.'outbound-endpoint'.each{ outbound ->
    def targetService = getOutboundTargetService(outbound,muleConfig.model)
	if ( targetService ){
		connections << [ getServiceNodeName(getOutboundService(outbound)), 
						 getServiceNodeName(targetService), 
						 getNsPrefix(outbound.namespaceURI()),
						 (outbound.@synchronous == 'true')]
	} else if (getNsPrefix(outbound.namespaceURI()) == 'http'){
		connections << [ getServiceNodeName(getOutboundService(outbound)), 
						 getExternalHttpServiceName(outbound), 
						 getNsPrefix(outbound.namespaceURI()),
						 (outbound.@synchronous == 'true')]
	} else if (getNsPrefix(outbound.namespaceURI()) == 'smtp'){
		connections << [ getServiceNodeName(getOutboundService(outbound)), 
						 getExternalSmtpServiceName(outbound), 
						 getNsPrefix(outbound.namespaceURI()),
						 (outbound.@synchronous == 'true')]
	}
  }
}
muleConfig.model.service.outbound.'*'.'http:outbound-endpoint'.each { outbound ->
	prnt dotFile, externalHttpEndpointNode(outbound)
}
muleConfig.model.service.outbound.'*'.'smtp:outbound-endpoint'.each { outbound ->
	prnt dotFile, externalSmtpEndpointNode(outbound)
}
connections.each {
     def properties = ""
     properties = "[label=\"${it[2]}\""
	 properties += ",dir=${it[3]?'both':'forward'}"
	 properties += "]"
     prnt dotFile, "${it[0]} -> ${it[1]} ${properties}"
}

 prnt dotFile, "}"