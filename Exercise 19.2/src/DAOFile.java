//import java.io.*;
//
//public class DAOFile
//{
//    private File daoFile = null;
//
//    public DAOFile()
//    {
//        // initialize the daoFile object
//    }
//
//    private void checkFile() throws IOException
//    {
//        // if the file doesn't exist, create it
//        if (!daoFile.exists())
//            daoFile.createNewFile();
//    }
//
//    public boolean setDAOName(String daoName)
//    {
//        // overwrite the file with the daoName string
//        // return a boolean to indicate if the file was created successfully
//        // print the stack trace if an IOException is thrown
//    }
//
//    public String getDAOName()
//    {
//        // get and return the name string
//        // print the stack trace and return null if an IOException is thrown
//    }
//
//    private void close(Closeable stream)
//    {
//        try
//        {
//            if (stream != null)
//                stream.close();
//        }
//        catch(IOException ioe)
//        {
//            ioe.printStackTrace();
//        }
//    }
//}