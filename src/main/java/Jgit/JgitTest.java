package Jgit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class JgitTest {


    public static void main(String[] args){
        //clone仓库
        //String remoteUrl="https://github.com/lxm0/test.git";
        //File repoDir= new File("F:\\IntelliJ IDEA\\Test");
       // gitClone(remoteUrl,repoDir);
        //获取所有的commit信息
        //getCommitAll();
        //获取所有分支
        //getBranches();
        //获取两个分支Diff
        //getBranchDiff();
        //获取两个commitDiff
        //getDiffCommit();


    }
    public static void getCommitAll(){
        try {
            Repository repository = new RepositoryBuilder().setGitDir(new File("F:\\IntelliJ IDEA\\njupt.nanyise\\.git")).build();
            //提取某个作者的提交，并打印相关信息
            Git git = Git.open(new File("F:\\IntelliJ IDEA\\njupt.nanyise\\.git"));
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Iterable<RevCommit> results = git.log().all().call();
            int count=0;
            for (RevCommit commit : results) {
                count++;
                System.out.println(count+": ");
                System.out.println("提交人：  " +commit.getAuthorIdent().getName());
                System.out.println("提交SHA1：  "+commit.getId().getName() );
                System.out.println("提交信息：  "+commit.getShortMessage());
                System.out.println("提交时间：  "+format.format(commit.getAuthorIdent().getWhen()) );
            }
            System.out.println("CommitsCount: "+count);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void getCommit(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Repository repository = new RepositoryBuilder().setGitDir(new File("F:\\IntelliJ IDEA\\njupt.nanyise\\.git")).build();
            RevWalk walk = new RevWalk(repository);
            Ref head = repository.findRef("HEAD");
            walk.markStart(walk.parseCommit(head.getObjectId())); // 从HEAD开始遍历，
            for (RevCommit commit : walk) {
                RevTree tree = commit.getTree();
                TreeWalk treeWalk = new TreeWalk(repository, repository.newObjectReader());
                PathFilter f = PathFilter.create("pom.xml");
                treeWalk.setFilter(f);
                treeWalk.reset(tree);
                treeWalk.setRecursive(false);
                while (treeWalk.next()) {
                    PersonIdent authoIdent = commit.getAuthorIdent();
                    System.out.println("提交人： " + authoIdent.getName() + " <" + authoIdent.getEmailAddress() + ">");
                    System.out.println("提交SHA1： " + commit.getId().name());
                    System.out.println("提交信息： " + commit.getShortMessage());
                    System.out.println("提交时间： " + format.format(authoIdent.getWhen()));

                    ObjectId objectId = treeWalk.getObjectId(0);
                    ObjectLoader loader = repository.open(objectId);
                    loader.copyTo(System.out);              //提取blob对象的内容
                }
            }
        }catch (Exception e){

        }
    }
    public static void getBranches(){
        try {
            Repository repository = new RepositoryBuilder().setGitDir(new File("F:\\IntelliJ IDEA\\njupt.nanyise\\.git")).build();
            System.out.println("Listing local branches:");

                Git git = new Git(repository);
                List<Ref> call = git.branchList().call();
                for (Ref ref : call) {
                    System.out.println("Branch: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());
                }

                System.out.println("Now including remote branches:");
                call = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
                for (Ref ref : call) {
                    System.out.println("Branch: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }



        }

    public static void getBranchDiff(){

            try {
                Repository repository =new RepositoryBuilder().setGitDir(new File("F:\\IntelliJ IDEA\\njupt.nanyise\\.git")).build();
                Git git = new Git(repository);

                // the diff works on TreeIterators, we prepare two for the two branches
                AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, "refs/heads/lixiaoman");
                AbstractTreeIterator newTreeParser = prepareTreeParser(repository, "refs/heads/master");

                // then the procelain diff-command returns a list of diff entries
                List<DiffEntry> diff = git.diff().setOldTree(oldTreeParser).setNewTree(newTreeParser).call();
                for (DiffEntry entry : diff) {
                    System.out.println("Entry: " + entry);
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    private static AbstractTreeIterator prepareTreeParser(Repository repository, String ref) throws IOException {
        // from the commit we can build the tree which allows us to construct the TreeParser
        Ref head = repository.exactRef(ref);
        try{
            RevWalk walk = new RevWalk(repository);
            RevCommit commit = walk.parseCommit(head.getObjectId());
            RevTree tree = walk.parseTree(commit.getTree().getId());

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
           ObjectReader reader = repository.newObjectReader();
                treeParser.reset(reader, tree.getId());


            walk.dispose();

            return treeParser;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public static void getDiffCommit(){
        try  {
            Repository repository = new RepositoryBuilder().setGitDir(new File("F:\\IntelliJ IDEA\\njupt.nanyise\\.git")).build();
            // The {tree} will return the underlying tree-id instead of the commit-id itself!
            // For a description of what the carets do see e.g. http://www.paulboxley.com/blog/2011/06/git-caret-and-tilde
            // This means we are selecting the parent of the parent of the parent of the parent of current HEAD and
            // take the tree-ish of it
            ObjectId oldHead = repository.resolve("HEAD^^^^{tree}");
            ObjectId head = repository.resolve("HEAD^^^{tree}");

            System.out.println("Printing diff between tree: " + oldHead + " and " + head);

            // prepare the two iterators to compute the diff between
            try  {
                ObjectReader reader = repository.newObjectReader();
                CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
                oldTreeIter.reset(reader, oldHead);
                CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
                newTreeIter.reset(reader, head);

                // finally get the list of changed files
                try {
                    Git git = new Git(repository);
                    List<DiffEntry> diffs= git.diff()
                            .setNewTree(newTreeIter)
                            .setOldTree(oldTreeIter)
                            .call();
                    for (DiffEntry entry : diffs) {
                        System.out.println("Entry: " + entry);
                    }
                }
                catch (Exception e){

                }
            }
            catch (Exception e){

            }
        }
        catch (Exception e){

        }

        System.out.println("Done");
    }
    public static void gitClone(String remoteUrl, File repoDir) {
        try {
            Git git = Git.cloneRepository()
                    .setURI(remoteUrl)
                    .setDirectory(repoDir)
                    .call();

            System.out.println("Cloning from " + remoteUrl + " to " + git.getRepository());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



}
