package nl.interaccess.zakenmagazijn;

import javax.ejb.Remote;

@Remote
public interface ZakenmagazijnRemote extends Zakenmagazijn {
    public Object process(Object request) throws Exception;
}
