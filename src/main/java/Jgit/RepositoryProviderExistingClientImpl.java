package Jgit;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;

public class RepositoryProviderExistingClientImpl implements RepositoryProvider{
    private String clientPath;
    public RepositoryProviderExistingClientImpl(String clientPath) {
        this.clientPath = clientPath;
    }

    public Repository get()  {
        try  {
            Repository repo = new FileRepository(clientPath);
            return repo;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
