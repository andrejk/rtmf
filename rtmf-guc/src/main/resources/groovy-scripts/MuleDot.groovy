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

def String wrap(input, linewidth = 70) throws IllegalArgumentException {
		if(input == null)
			throw new IllegalArgumentException("Input String must be non-null")
		if(linewidth <= 1)
			throw new IllegalArgumentException("Line Width must be greater than 1")

		input = input.replaceAll(/\s+/,' ')
		def olines = []
		def oline = ""
		
		input.split(" ").each() { wrd ->
			if( (oline.size() + wrd.size()) <= linewidth ) {
				oline <<= wrd <<= " "
			}else{
				olines += oline
				oline = wrd + " "
			}
		}
		olines += oline
		result = ""
		olines.each { result += it + '<br/>'}
		return result
}

def nameMaker(name){
  return name.toString().replace("\${","").replace("}","_").replace(".","_").replace("/","_").replace("-","_").replace(":","_")
}
def htmlEncode(path){
  return path.toString().replace("\$","&#36;").replace("{","&#123;").replace("}","&#125;")
}
def stripProtocol(url){
	return url.replaceAll(/^[a-z]+:\/\//,"")
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
def getNodeName(muleObject){
	assert muleObject != null
	def name = ""
	switch (muleObject.name()) {
		case 'outbound-endpoint' :
			name = nameMaker(muleObject.parent().parent().parent().@name.toString() +
				"_" + getNsPrefix( muleObject.namespaceURI()) + "_" + muleObject.@path  + "_out")
			break
		case 'inbound-endpoint' :
			name = nameMaker( getNsPrefix( muleObject.namespaceURI()) + "_" + muleObject.@path  + "_in")
			break
		case 'pass-through-router' :
		case 'filtering-router' :
		case 'multicasting-router' :
		    name = nameMaker("${muleObject.parent().parent().@name}_${muleObject.name()}")
			break
		default :
			throw new Exception("Unknown object")
	}
	return name
}
def getTargetNodeName(muleObject){
	assert muleObject != null
	def name = "xxx"
	switch (muleObject.name()){
		case 'outbound-endpoint':
			switch (getNsPrefix(muleObject.namespaceURI())){
				case 'http':
					def endpoint = muleObject
					name = nameMaker("http://" + endpoint.@host.toString() +
						":" + endpoint.@port.toString() + "/" + endpoint.@path.toString())
					break
				case 'vm':
					name = nameMaker( getNsPrefix( muleObject.namespaceURI()) + "_" + muleObject.@path  + "_in")
					break
				case 'file':
					name = nameMaker(getNsPrefix(muleObject.namespaceURI()) + "_" + muleObject.@path  + "_in")
					break
				default:
					name = "xxx"
			}
			break
		default :
			break
	}
	return name
}
def getReplyToNodeName(replyTo,service){
	def protocol = ((replyTo.@address) =~ /[a-z]+/)[0]
	def url = stripProtocol(replyTo.@address.toString())
	def endpoint = (service.'async-reply'.'inbound-endpoint'.findAll { it -> it.@path == url })[0]
	return getNodeName(endpoint)
}
def getComponentNodeName(service,component){
	assert service != null
	return "${service.@name}_component"
}
def httpEndpointUri(endpoint){
  return htmlEncode( "http://" + endpoint.@host.toString() + ":" + endpoint.@port.toString() + "/" + endpoint.@path.toString())
}
def endpointNode(endpoint){
	def txt = getNodeName(endpoint)
	txt += "[shape=record"
	txt += ", label=<<TABLE cellspacing=\"0\" border=\"0\" ALIGN=\"LEFT\">"
	txt += "<TR><TD ALIGN=\"LEFT\">Type:</TD><TD ALIGN=\"LEFT\">" + getNsPrefix(endpoint.namespaceURI()) + "-" + endpoint.name() + "</TD></TR>"
	txt += "<TR><TD ALIGN=\"LEFT\">Name:</TD><TD ALIGN=\"LEFT\">" + endpoint.@name + "</TD></TR>"
	txt += "<TR><TD ALIGN=\"LEFT\">Path:</TD><TD ALIGN=\"LEFT\">" + htmlEncode(endpoint.@path) + "</TD></TR>"
	txt += "<TR><TD ALIGN=\"LEFT\">Transformers:</TD><TD ALIGN=\"LEFT\">" + endpoint.'@transfomer-refs' + "</TD></TR>"
	txt += "<TR><TD ALIGN=\"LEFT\">Response Transformers:</TD><TD ALIGN=\"LEFT\">" + endpoint.'@responseTransformer-refs' + "</TD></TR>"
	txt += "</TABLE>>"
	txt += "]"
	return txt
}
def externalHttpEndpointNode(outbound){
	return "${getTargetNodeName(outbound)} [shape=ellipse,label=\"${httpEndpointUri(outbound)}\"]"
}
def router(outrouter){
	//label=\"multicasting-router\"
    //prnt dotFile, "  ${getNodeName(outrouter)} [shape=rectangle,label=<<TABLE><TR><TD><IMG SRC=\"ContentBasedRouterIcon.gif\"/></TD></TR></TABLE>>]"
	return "  ${getNodeName(outrouter)} [shape=rectangle,label=\"" + outrouter.name() + "\"]"
}
def prnt(file,line){
	file << (line + '\n')
}
def connections = []
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
prnt dotFile, " concentrate=true"
prnt dotFile, " graph [fontname=\"Arial\", fontsize = 32, label = \"rtmfguc\" ]";
prnt dotFile, " node [fontname=\"Arial\", fontsize = 32 ]";
muleConfig.model.service.each { service ->
  prnt dotFile, "subgraph cluster_${service.@name} {"
  prnt dotFile, "  shape=component"
  prnt dotFile, "  style=filled"
  prnt dotFile, "  color=grey97"	
  prnt dotFile, "  label =  \"${service.@name}\""
  prnt dotFile, "  ${service.@name}_description [shape=note,label=<<font>${wrap(service.description.text(),40)}</font>>,color=lemonchiffon,style=filled]"

  /*****************************************************************************
   * inbound
   ****************************************************************************/
   
  prnt dotFile, "  subgraph cluster_${service.@name}_inbound { "
  prnt dotFile, "    color=grey94 "
  prnt dotFile, "    label=\"inbound\""
  service.inbound.'inbound-endpoint'.each { inbound ->
    prnt dotFile, endpointNode(inbound)
    connections << [getNodeName(inbound),getComponentNodeName(service,service.component)]
  }
  prnt dotFile, "}" // end inbound cluster
  
  /*****************************************************************************
   * component
   ****************************************************************************/

  prnt dotFile, "  ${service.@name}_component [shape=component,label=\"component\"]"

  /*****************************************************************************
   * outbound
   ****************************************************************************/

  prnt dotFile, "  subgraph cluster_${service.@name}_outbound {"
  prnt dotFile, "    color=grey94 "
  prnt dotFile, "    label=\"outbound\""
  prnt dotFile, "  subgraph cluster_${service.@name}_outbound_router {"
  prnt dotFile, "    color=grey90 "
  prnt dotFile, "    label=\"routers\""
  service.outbound.'pass-through-router'.each { outrouter ->
    connections << [getComponentNodeName(service,service.component),getNodeName(outrouter)]
    prnt dotFile, router(outrouter)

    outrouter.'outbound-endpoint'.each { outbound ->
      prnt dotFile, endpointNode(outbound)
      connections << [getNodeName(outrouter),getNodeName(outbound)]
      connections << [getNodeName(outbound),getTargetNodeName(outbound)]
    }
  }
  if ( service.outbound.'filtering-router'.size() > 0 ) {
    connections << [getComponentNodeName(service,service.component),getNodeName(service.outbound.'filtering-router'[0])]
    prnt dotFile, router(service.outbound.'filtering-router'[0])
    service.outbound.'filtering-router'.'outbound-endpoint'.each { outbound ->
      connections << [getNodeName(outbound.parent()),getNodeName(outbound)]
      prnt dotFile, endpointNode(outbound)
      connections << [getNodeName(outbound),getTargetNodeName(outbound)]
    }
  }
  service.outbound.'multicasting-router'.each { outrouter ->
	connections << [getComponentNodeName(service,service.component),getNodeName(outrouter)]
	prnt dotFile, router(outrouter)
    outrouter.'outbound-endpoint'.each { outbound ->
		connections << [getNodeName(outrouter),getNodeName(outbound)]
		prnt dotFile, endpointNode(outbound)
		connections << [getNodeName(outbound),getTargetNodeName(outbound)]
    }
    outrouter.'reply-to'.each { replyto ->
    	replyto.parent().'outbound-endpoint'.each { oe ->
	    	connections << [getTargetNodeName(oe),getReplyToNodeName(replyto,service),oe.@synchronous,true]
    	}
    }
  }
  prnt dotFile, "}" // end outbound router cluster

  /*****************************************************************************
   * async reply
   ****************************************************************************/
   service.'async-reply'.each { reply ->
		prnt dotFile, "  subgraph cluster_${service.@name}_asyncreply {"
		prnt dotFile, "    color=grey90 "
		prnt dotFile, "    label=\"async-reply\""
		reply.'inbound-endpoint'.each { inbound ->
		    prnt dotFile, endpointNode(inbound)
			connections << [getNodeName(inbound),getComponentNodeName(service,service.component),inbound.@synchronous]
		}
		prnt dotFile, "  }" // end async-reply cluster
   }
  prnt dotFile, "}" //end outbound cluster
        
  
  prnt dotFile, "}" // end service component cluster
 }
  muleConfig.model.service.outbound.'*'.'http:outbound-endpoint'.each { outbound ->
    prnt dotFile, externalHttpEndpointNode(outbound)
  }
 connections.each {
     def constraint = ""
     if (it[3]){
     	constraint = "[constraint=false]"
     }
     prnt dotFile, "${it[0]} -> ${it[1]} ${constraint}"
 }
 
prnt dotFile, "}"