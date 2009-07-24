package nl.rotterdam.shared.docloods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class DocLoodsApplications {
    
    private Map<String,DocLoodsApplication> applicationMappings;
    private Map<String,DocLoodsApplication> extensionMappings;
    private List<DocLoodsApplication> applications;

    public DocLoodsApplications(List<DocLoodsApplication> toepassingen) {
        init(toepassingen);
    }
    
    private DocLoodsApplications() {
        List<DocLoodsApplication> data = new ArrayList<DocLoodsApplication>();
        
        // 1ste extensie in de lijst is de default-extensie
        data.add(application("ACROBAT","Adobe Acrobat",new String[] {"PDF"}));
        data.add(application("AUTOCAD","AUTOCAD",new String[] {"DWG"}));
        data.add(application("BIQ REPORTS","BI Query",new String[] {"REP"}));
        data.add(application("CITERITE","CiteRite",new String[] {"CIT"}));
        data.add(application("DELTAVIEW","Deltaview",new String[] {"WDF"}));
        data.add(application("MS PICTMAN","Micosoft Picture Manager",new String[] {"TIF","GIF","BMP","PNG","JPG","WMF","TIFF"}));
        data.add(application("L123-97","L123-97",new String[] {"123"}));
        data.add(application("LOTUS WORD PRO","LOTUS WORD PRO",new String[] {"LWP"}));
        data.add(application("MS EXCEL","MS EXCEL",new String[] {"XLS","CSV","XLSX"}));
        data.add(application("IEXPLORE","MS Internet Explorer",new String[] {"HTML","HTM","XML"}));
        data.add(application("MEDIAPLAYER","MS Media Player",new String[] {"MPG","AVI","WMV"}));
        data.add(application("MS OUTLOOK","MS OUTLOOK",new String[] {"MSG"}));
        data.add(application("MS POWERPOINT","MS POWERPOINT",new String[] {"PPT","PPS","PPTX"}));
        data.add(application("MS PROJECT","MS PROJECT 2003",new String[] {"MPP"}));
        data.add(application("VISIO","MS VISIO",new String[] {"VSD"}));
        data.add(application("MS WORD","MS WORD",new String[] {"DOC","RTF","DOCX"}));
        data.add(application("PRESENTATIONS","PRESENTATIONS",new String[] {"SHW"}));
        data.add(application("QPW","Quattro Pro",new String[] {"QPW"}));
        data.add(application("KLADBLOK","Kladblok",new String[] {"TXT","SQL","LOG","INI"}));
        data.add(application("WORDPERFECT","WORDPERFECT",new String[] {"WPD"}));
        data.add(application("MICROSTATION","Microstation",new String[] {"DGN"}));
        data.add(application("SVG","Scalable Vector Graphics",new String[] {"SVG"}));
        data.add(application("DEF","Drawing Exchange Format",new String[] {"DXF"}));
        
        init(data);
    }
    
    private DocLoodsApplication application(String name,String description,String [] extensions) {
        DocLoodsApplication app = new DocLoodsApplication();
        app.set("APPLICATION",name);
        app.set("DESCRIPTION",description);
        app.set("DEFAULT_EXTENSION",extensions[0]);
        StringBuilder ft = new StringBuilder();
        boolean first = true;
        for (String extension : extensions) {
            if (!first) ft.append(",");
            ft.append(extension);
            ft.append(";");
            ft.append(extension);
        }
        app.set("FILE_TYPES",ft.toString());
        return app;
        
    }
    
    private void init(List<DocLoodsApplication> toepassingen) {
        applications = toepassingen;

        applicationMappings = new HashMap<String,DocLoodsApplication>();
        extensionMappings = new HashMap<String,DocLoodsApplication>();
        
        try {
            for (DocLoodsApplication toepassing : toepassingen) {
                String application = toepassing.getName();
                applicationMappings.put(application,toepassing);
                String[] extensions = toepassing.getFileTypes();
                for (String extension : extensions) {
                    extensionMappings.put(extension.toUpperCase(),toepassing);
                }
            }
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
    }
    
    private static DocLoodsApplications instance;
    private static synchronized DocLoodsApplications getInstance() {
        if (instance == null) {
            instance = new DocLoodsApplications();
        }
        return instance;
    }
    
    public DocLoodsApplication getApplicationByExtension(String extension) {
        if (extension == null) return null;
        return extensionMappings.get(extension.toUpperCase());
    }
    
    public List<DocLoodsApplication> getApplications() {
        return applications;
    }
    
    public String[] getExtensions(String application) {
        DocLoodsApplication dlApplication = applicationMappings.get(application);
        if (dlApplication == null) return null;
        return dlApplication.getFileTypes();
    }
    
    public String getDefaultExtension(String application) {
        DocLoodsApplication dlApplication = applicationMappings.get(application);
        if (dlApplication == null) return null;
        return dlApplication.getDefaultExtension();
    }
    
    public String getExtension(String file) {
        int pos = file.lastIndexOf('.');
        if (pos >= 0) {
            return file.substring(pos+1);
        }
        return null;
    }
    
    public DocLoodsApplication getApplicationByFile(String file) {
        return getApplicationByExtension(getExtension(file));
    }
    
}
