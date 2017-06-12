import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ZhangGang on 2017/5/18.
 */
public class FileViewer {



    /**
     * 根据文件夹路径，读取所有子目录里包含指定类型的文件。
     * @param dirPath
     * <li>eg:E:\app\agreeWeb\workagree\fly.info\context\images\fly\信息发布\2012\02\08\02\32\27\227\89857567\</li>
     * @param fileTypes
     * <li>eg:jpg,gif,jpeg,bmp</li>
     * @return 所有符合指定类型文件的文件路径
     * <li>eg:E:\app\agreeWeb\workagree\fly.info\context\images\fly\信息发布\2012\02\08\02\32\27\227\89857567\1-1\1-1-2\1.gif</li>
     */
    public static List<String> getAllFilePathByDir_FilterByFileType(String dirPath,
                                                                    List<String> fileTypes) {

        String localClassFilePath = FileViewer.class.getResource("/").getPath();
        System.out.println(localClassFilePath);
        String contextFilePath = localClassFilePath.substring(1, localClassFilePath.length()-16);
        contextFilePath = contextFilePath.replace("/", "\\");
        System.out.println(contextFilePath);

        List<String> filePathList = new ArrayList<String>();
        for (int index = 0; index < fileTypes.size(); index++) {
            fileList = new ArrayList<String>();
            List arrayList = FileViewer.getListFiles(dirPath, fileTypes.get(index), true);
            if (arrayList.isEmpty()) {
                System.out.println("没有符号要求的文件");
            } else {
                String message = "";
                message += "符号要求的文件数：" + arrayList.size();
                System.out.println(message);
                for (Iterator i = arrayList.iterator(); i.hasNext();) {
                    String temp = (String) i.next();
                    //E:\app\agreeWeb\workagree\fly.info\context\images\fly\信息发布\2012\02\08\06\05\06\26\90852629\1-1\1-1-1\1.jpg
                    temp = temp.replace(contextFilePath, "");//把根路径去掉
                    System.out.println(temp);
                    filePathList.add(temp);
                    message += temp + "/r/n";
                }
            }
        }
        for (int replaceIndex = 0; replaceIndex < filePathList.size(); replaceIndex++) {
            filePathList.set(replaceIndex, filePathList.get(replaceIndex).replace("\\", "/"));
        }
        return filePathList;
    }

    public static List<String> fileList = new ArrayList<String>();

    /**
     *
     * @param path
     *            文件路径
     * @param suffix
     *            后缀名
     * @param isdepth
     *            是否遍历子目录
     * @return
     */
    public static List getListFiles(String path, String suffix, boolean isdepth) {
        File file = new File(path);
        return FileViewer.listFile(file, suffix, isdepth);
    }

    public static List listFile(File f, String suffix, boolean isdepth) {
        // 是目录，同时需要遍历子目录
        if (f.isDirectory() && isdepth == true) {
            File[] t = f.listFiles();
            for (int i = 0; i < t.length; i++) {
                listFile(t[i], suffix, isdepth);
            }
        } else {
            String filePath = f.getAbsolutePath();

            if (suffix != null) {
                int begIndex = filePath.lastIndexOf(".");// 最后一个.(即后缀名前面的.)的索引
                String tempsuffix = "";

                if (begIndex != -1)// 防止是文件但却没有后缀名结束的文件
                {
                    tempsuffix = filePath.substring(begIndex + 1, filePath
                            .length());
                }

                if (tempsuffix.equals(suffix)) {
                    fileList.add(filePath);
                }
            } else {
                // 后缀名为null则为所有文件
                fileList.add(filePath);
            }

        }

        return fileList;
    }


}
