import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiamondTable {
    private String metaPath;
    private String linePath;
    private List<String> rules =  new ArrayList<>(1);
    private List<String> types = new ArrayList<>(1);

    private String name;

    public DiamondTable(String metaPath) {
        if(new File(metaPath).exists())
        {
            this.metaPath = metaPath;
            read();
        }
        else{
            try {
                new File(metaPath).createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean update(String operation,List<String> arguments)
    {

        return false;
    }

    public boolean read()
    {
        String remain, content;

        try(InputStream stream = new FileInputStream(this.metaPath))
        {
            //lettura per vedere se il file e vuoto o meno.
            remain = new String(stream.readNBytes(1));

            if(!remain.isEmpty())
            {

                //lettura del file tramite buffer per non leggere il file direttamente , potrebbe occupare la ram!
                BufferedInputStream reader = new BufferedInputStream(new FileInputStream(this.metaPath));
                byte[] bytes = new byte[2048];
                int bytesRead;
                StringBuilder stringBuilder = new StringBuilder();

                while ((bytesRead = reader.read(bytes)) != -1) {
                    String str = new String(bytes, 0, bytesRead, StandardCharsets.UTF_8);
                    stringBuilder.append(str);
                }

                content = stringBuilder.toString();

                //annalizamento tabella

                if (content.contains("<diam")&&content.contains("diam/>"))
                {
                    try {
                        String dirty = content.replaceFirst("\"",":");
                        String name = dirty.substring(dirty.indexOf(":")+1,dirty.indexOf("\""));
                        dirty = dirty.replace(dirty.substring(dirty.indexOf("tableName"),dirty.indexOf("\"")),"").replaceFirst("\"","");


                        if(!name.isEmpty())
                        {
                            this.name = name;
                        }

                        String tmp1 = dirty.substring(dirty.indexOf("(")+1,dirty.indexOf(")"));
                        dirty = dirty.replace(dirty.substring(dirty.indexOf("("),dirty.indexOf(")")+1),"");
                        String tmp2 = dirty.substring(dirty.indexOf("(")+1,dirty.indexOf(")"));

                        this.types.addAll(Arrays.asList(tmp1.split("/,")));

                        if (!tmp2.isEmpty()) {
                            this.rules.addAll(Arrays.asList(tmp2.split("/,")));
                        }

                        if(!this.rules.isEmpty()&&this.rules.size() == this.types.size())
                        {
                            throw new RuntimeException("The size of rules list is not equals to the columns of table");
                        }

                        dirty = dirty.replace(dirty.substring(dirty.indexOf("("),dirty.indexOf(")")+1),"").replaceFirst("\"",":");

                        String data = dirty.substring(dirty.indexOf(":")+1,dirty.indexOf("\""));

                        if(new File(data).exists())
                        {
                            this.linePath = new File(data).getAbsolutePath();

                            //controlla dati della tabella se sono ommegeni all tipo di dati indicati nel tipo e se sono presenti collone in piu o meno.
                            return verifyData();
                        }
                        else{
                            String nameFile = new File(this.metaPath).getAbsolutePath().replace(new File(this.metaPath).getName(),"") + RandomGen.getRandomName(16) + ".data";
                            if(new File(nameFile).createNewFile())
                            {
                                this.linePath = new File(nameFile).getAbsolutePath();
                            }
                            else{
                                throw new RuntimeException("The table don't contain data section and can't create a new data section");
                            }
                        }


                    } catch (Exception e) {
                        throw new RuntimeException("Table is not valid!",e);
                    }

                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    private boolean verifyData()
    {
        return false;
    }

    public boolean create(String metaPath, List<String> types,List<String> rules)
    {
        return false;
    }


    @Override
    public String toString() {
        return "DiamondTable{" +
                "metaPath='" + metaPath + '\'' +
                ", linePath='" + linePath + '\'' +
                ", rules=" + rules +
                ", types=" + types +
                ", name='" + name + '\'' +
                '}';
    }
}