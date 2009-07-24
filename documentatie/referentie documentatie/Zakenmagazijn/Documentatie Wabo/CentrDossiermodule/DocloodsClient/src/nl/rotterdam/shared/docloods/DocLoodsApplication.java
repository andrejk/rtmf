package nl.rotterdam.shared.docloods;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.Set;

import nl.rotterdam.shared.dms.KeyValue;

public class DocLoodsApplication {
    public static final String[] FIELDS = {"APPLICATION", "DESCRIPTION", "DEFAULT_EXTENSION", "FILE_TYPES"};
    private Map<String,String> fieldMap = new HashMap<String,String>();
    private List<String> lFileTypes;
    
    public static final String FORM_NAME = "CYD_CMNAPPS";
    private static KeyValue[] searchFields;
    
    public DocLoodsApplication() {
        lFileTypes = new ArrayList<String>();
    }
    
    private static void initSearchValues() {
        searchFields = new KeyValue[2];
        searchFields[0] = new KeyValue();
        searchFields[0].setKey("DISABLED");
        searchFields[0].setValue("N");
        searchFields[1] = new KeyValue();
        searchFields[1].setKey("DEFAULT_EXTENSION");
        searchFields[1].setValue("*");
    }
    
    // getters and setters
    public void set(String key, String value) {
        fieldMap.put(key, value);
        
        // filetypes.value is a comma-separated list of semicolon-separated extension-description pairs
        if (key.equals("FILE_TYPES")) {
            String[] extensionDescriptionArray = value.split(",");
            for (int idx=0; idx<extensionDescriptionArray.length; idx++) {
                String[] temp = extensionDescriptionArray[idx].split(";");
                if (!lFileTypes.contains(temp[0])) {
                    lFileTypes.add(temp[0]);
                }
            }
        }

        if (key.equals("DEFAULT_EXTENSION")) {
            if (!lFileTypes.contains(value)) {
                lFileTypes.add(value);
            }
        }
    }
    
    public String get(String key) {
         return fieldMap.get(key);
    }
    
    public String getName() {
        return get("APPLICATION");
    }
    
    public String getDescription() {
        return get("DESCRIPTION");
    }
    
    public String getDefaultExtension() {
        return get("DEFAULT_EXTENSION");
    }
    
    public String[] getFileTypes() {
        String [] fileTypes = new String[lFileTypes.size()];
        for (int idx=0; idx<lFileTypes.size(); idx++) {
            fileTypes[idx] = lFileTypes.get(idx);
        }
        return fileTypes;
    }
    
    public static KeyValue[] getSearchFields() {
        if (searchFields == null) initSearchValues();
        return searchFields;
    }
    
    public boolean isFileTypeAssociated(String fileType) {
        if (fileType == null) {
            return false;
        }
        
        boolean retval = false;
        for (int idx=0; idx<lFileTypes.size(); idx++) {
            if (fileType.equalsIgnoreCase(lFileTypes.get(idx))) {
                retval = true;
                break;
            }
        }
        return retval;
    }
}
