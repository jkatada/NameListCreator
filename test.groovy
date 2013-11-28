class GroovyArchitecture {
     static void main(String[] args) {
        runArchitectureRules(new File("architecture.groovy"))
     }
     static void runArchitectureRules(File dsl) {
         Script dslScript = new GroovyShell().parse(dsl.text)
 
         dslScript.metaClass = createEMC(dslScript.class, {
             ExpandoMetaClass emc ->
             emc.architecture = {
                   Closure cl ->
             }
 
         })
         dslScript.run()
     }
 
     static ExpandoMetaClass createEMC(Class clazz, Closure cl) {
         ExpandoMetaClass emc = new ExpandoMetaClass(clazz, false)
 
         cl(emc)
 
         emc.initialize()
         return emc
     }
 }