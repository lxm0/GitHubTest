package Jgit;



import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
/*
 *
 * Child 子版本号
 * Parent 父版本号
 */
public class JgitDiff {

    public JgitDiff() {
    }
    static String URL="F:\\IntelliJ IDEA\\cpython\\.git";
    static Git git;
    public static Repository repository;
    public static void main(String[] args) {
        JgitDiff jgitDiff = new JgitDiff();

        jgitDiff.diffMethod("38df97a03c5102e717a110ab69bff8e5c9ebfd08","5a087d5401e6956cf4c6d95f15fedabf39a4f5af");
    }
    /*
     *
     */
    public void diffMethod(String Child, String Parent){
        try {
            git=Git.open(new File("F:\\IntelliJ IDEA\\cpython\\.git"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        repository=git.getRepository();
        ObjectReader reader = repository.newObjectReader();
        CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();

        try {
            ObjectId old = repository.resolve(Child + "^{tree}");
            ObjectId head = repository.resolve(Parent+"^{tree}");
            oldTreeIter.reset(reader, old);
            CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
            newTreeIter.reset(reader, head);
            List<DiffEntry> diffs= git.diff()
                    .setNewTree(newTreeIter)
                    .setOldTree(oldTreeIter)
                    .call();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DiffFormatter df = new DiffFormatter(out);
            df.setRepository(git.getRepository());

            for (DiffEntry diffEntry : diffs) {
                df.format(diffEntry);
                String diffText = out.toString("UTF-8");
                System.out.println(diffText);
                //  out.reset();
            }
        } catch (IncorrectObjectTypeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }
}


