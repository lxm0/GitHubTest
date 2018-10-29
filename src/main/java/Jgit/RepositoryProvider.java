package Jgit;

import org.eclipse.jgit.lib.Repository;

public interface RepositoryProvider {
    Repository get() ;
}
