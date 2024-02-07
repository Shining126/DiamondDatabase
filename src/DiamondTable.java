import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DiamondTable {
    private String metaPath;
    private String linePath;
    private ArrayList<String> rules;
    private ArrayList<String> types;

    public DiamondTable(String metaPath) {
        if(new File(metaPath).exists())
        {
            this.metaPath = metaPath;
        }
    }

    private boolean update()
    {

        return false;
    }

    private boolean read()
    {

        return false;
    }

    public boolean create(String metaPath, List<String> types,List<String> rules)
    {
        return false;
    }


}