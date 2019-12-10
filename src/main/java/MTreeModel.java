import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;

public class MTreeModel extends DefaultTreeModel {
    DefaultMutableTreeNode rootNode = null;

    public MTreeModel() {
        super(new DefaultMutableTreeNode("Найденные файлы:"));
        rootNode = (DefaultMutableTreeNode) getRoot();
    }

    public void load(ArrayList<String> allFind) {
        rootNode.removeAllChildren();
        for (String s : allFind) {

            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(s);
            rootNode.add(childNode);
        }

        nodeStructureChanged(rootNode);
    }

    public TreeNode getRootNode() {
        return rootNode;
    }
}
