println "Running MuleDot"
projDir = "rtmf-guc"
siteDir = projDir + "/target/docs/"
groovyDir = projDir + "/src/main/resources/groovy-scripts/"
muleConfigDir = projDir + "/src/main/resources/"
def ant = new AntBuilder()
ant.mkdir(dir: siteDir)

File script = new File(groovyDir + "MuleDot.groovy")
File scriptHl = new File(groovyDir + "MuleDotHL.groovy")

if (script.exists()) {
    files = (new File(muleConfigDir)).listFiles().grep(~/.*rtmfguc.*config.xml$/)
    for (muleFile in files) {
        baseFilename = muleFile.name[0..-5]
        println "baseFilename ${baseFilename}"
        def shell = new GroovyShell()
        println "converting mule to dot"
        def result = shell.run(script, [muleFile.absolutePath.toString(), (siteDir + baseFilename + ".dot")])
        println "converting mule to hl dot"
        def resultHl = shell.run(scriptHl, [muleFile.absolutePath.toString(), siteDir + baseFilename + "-hl.dot"])
        println "running dot to generate image files"
        try {
            ["dot", "-Tpng", siteDir + baseFilename + ".dot", "-o", siteDir + baseFilename + ".png"].execute()
            ["dot", "-Tsvg", siteDir + baseFilename + ".dot", "-o", siteDir + baseFilename + ".svg"].execute()
            ["dot", "-Tpdf", siteDir + baseFilename + ".dot", "-o", siteDir + baseFilename + ".pdf"].execute()
            ["dot", "-Tpng", siteDir + baseFilename + "-hl.dot", "-o", siteDir + baseFilename + "-hl.png"].execute()
            ["dot", "-Tsvg", siteDir + baseFilename + "-hl.dot", "-o", siteDir + baseFilename + "-hl.svg"].execute()
            ["dot", "-Tpdf", siteDir + baseFilename + "-hl.dot", "-o", siteDir + baseFilename + "-hl.pdf"].execute()
        } catch (e) {
            println "Failed to run dot on mule graph, probably due to missing dot executable"
        }
    }
} else {
    event("StatusError", ["Scripts missing"])
}
