import java.io.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class  InfectStatistic{
    public String logpath;//输入日志文件
    public String outpath;//输出日志文件

    /*InfectStatistic
        感染类型
     */
    public int[] type={1,1,1,1};//感染类型，为0不输出，为1输出
    public String[] type_str={"感染患者","疑似患者","治愈","死亡"};

    /*
       省份统计和计数
     */
    public int[] province=new int[35];//统计各省是否需要输出
    public String[] province_str={"全国","安徽","北京","重庆","福建","甘肃",
            "广东","广西","贵州","海南","河北","河南","黑龙江","湖北",
            "湖南","吉林","江苏","江西","辽宁","内蒙古","宁夏","青海",
            "山东","山西","陕西","上海","四川","天津","西藏", "新疆",
            "云南","浙江","澳门","香港","台湾"};//各省名称

    /*
        人数统计 不同的35个省份的人数
     */
    public int[][] per=new int[35][4];//各省不同类型患者人数统计

    /*
    获取当前系统时间
     */
    SimpleDateFormat date=new SimpleDateFormat("yyyy-mm-dd");
    Date d=new Date(System.currentTimeMillis());//获取系统当前时间
    public String date_now=date.format(d);//将当前时间转换为固定格式




    /*
    处理命令行
     */
    class cmd {
        String[] cmd_str;//保存命令行

        cmd(String[] cmd) {
            cmd_str = cmd;
            province[0] = 0;//默认全国数据不输出
        }

        public boolean cmd_e()//对命令行不同的参数进行处理
        {
            int i;
            if (!cmd_str[0].equals("list")) {//判断是否是list命令
                System.out.println("命令行非list错误");
                return false;
            }
            for (i = 1; i < cmd_str.length; i++) {
                if (cmd_str[i].equals("-log")) {//读取-log参数
                    i++;
                    i = get_log(i);
                    if (i == -1) {
                        System.out.println("log参数出错");
                        return false;
                    }
                } else if (cmd_str[i].equals("-out")) {//读取-out参数
                    i++;
                    i = get_out(i);
                    if (i == -1) {
                        System.out.println("out参数出错");
                        return false;
                    }
                } else if (cmd_str[i].equals("-date")) {//读取date参数
                    i++;
                    i = get_date(i);
                    if (i == -1) {
                        System.out.println("date参数出错");
                        return false;
                    }
                } else if (cmd_str[i].equals("-type")) {//读取type参数
                    i++;
                    i = get_type(i);
                    if (i == -1) {
                        System.out.println("type参数出错");
                        return false;
                    }
                } else if (cmd_str[i].equals("-province")) {//读取province参数
                    i++;
                    i = get_province(i);
                    if (i == -1) {
                        System.out.println("province参数出错");
                    }
                }
            }
            return true;
        }

        public int get_log(int m)//获取日志文件的位置
        {
            if (m < cmd_str.length) {
                if (cmd_str[m].matches("^[A-Z]:\\\\(.+?\\\\)*$")) {//判断是否符合文件路径的正则表达式
                    logpath = cmd_str[m];
                } else
                    return -1;
            } else
                return -1;
            return m;
        }

        public int get_out(int m) {//获取输出日志位置
            if (m < cmd_str.length) {
                if (cmd_str[m].matches("^[A-z]:\\\\(\\S+)+(\\.txt)$")) {//判断是否符合文件路径的正则表达式
                    outpath = cmd_str[m];
                } else
                    return -1;
            } else
                return -1;
            return m;
        }

        public int get_date(int m)//获取日期
        {
            if (m < cmd_str.length) {
                if (date_now.compareTo(cmd_str[m]) >= 0) {//判断输入日期是否超过当前日期
                    date_now = cmd_str[m] + ".log.txt";
                } else
                    return -1;
            } else
                return -1;
            return m;
        }

        public int get_type(int m)//获取类型
        {
            int n;
            n = m - 1;
            if (m < cmd_str.length) {
                for (int i = 0; i < 4; i++)//将type输出默认为不输出
                {
                    type[i] = 0;
                }
                while (m < cmd_str.length) {
                    if (cmd_str[m].equals("ip")) {
                        type[0] = 1;
                        m++;
                    } else if (cmd_str[m].equals("sp")) {
                        type[1] = 1;
                        m++;
                    } else if (cmd_str[m].equals("cure")) {
                        type[2] = 1;
                        m++;
                    } else if (cmd_str[m].equals("dead")) {
                        type[3] = 1;
                        m++;
                    } else
                        break;
                }
                m--;
            }

            if (n == m) {//表示type后面没有参数
                for (int i = 0; i < 4; i++) {
                    type[i] = 1;//默认全部输出
                }
            }
            return m;
        }

        public int get_province(int m) {
            int n = m;
            if (m < cmd_str.length) {
                province[0] = 1;
                while (m < cmd_str.length) {
                    for (int i = 0; i < province_str.length; i++) {//根据省份输出 使该省为可输出状态
                        if (cmd_str[m].equals(province_str[i])) {
                            province[i] = 1;
                            m++;
                            break;
                        }
                    }
                }
            }
            if (n == m)
                return -1;
            return (m - 1);
        }
    }

    /**
     *  处理文件
     */
    class file_handle
    {
        file_handle(){};

        public void get_list()
        {
            int m;
            File[] list;
            String file_name;
            File file=new File(logpath);
            list=file.listFiles();

            for(m=0;m<list.length;m++)
            {
                file_name=list[m].getName();
                if(file_name.compareTo(date_now)<=0){
                    read_txt(logpath+file_name);
                }

            }
        }

        /**
         *  读取文本
         */
        public void read_txt(String log)//读取文本
        {
            try{
                BufferedReader br;
                FileReader pr=new FileReader(log);
                br=new BufferedReader(new InputStreamReader(
                        new FileInputStream(new File(log)), "UTF-8"));
                String contentLine;//按行读取
                while((contentLine=br.readLine())!=null){
                    if (!contentLine.startsWith("//")){
                        deal_txt(contentLine);//对每行文件进行处理
                    }
                }
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
        }

        /**
         *  处理文本
         */
        public void deal_txt(String line)
        {
            String str1="(\\S+) 新增 感染患者 (\\d+)人";
            String str2="(\\S+) 新增 疑似患者 (\\d+)人";
            String str3="(\\S+) 感染患者 流入 (\\S+) (\\d+)人";
            String str4="(\\S+) 疑似患者 流入 (\\S+) (\\d+)人";
            String str5="(\\S+) 死亡 (\\d+)人";
            String str6="(\\S+) 治愈 (\\d+)人";
            String str7="(\\S+) 疑似患者 确诊感染 (\\d+)人";
            String str8="(\\S+) 排除 疑似患者 (\\d+)人";

            if(line.matches(str1))
            {
                String[] txt_str=line.split(" ");
                txt_str[3]=txt_str[3].replace("人","");
                int int1;
                int1=Integer.valueOf(txt_str[3]);
                for(int i=0;i<province_str.length;i++)
                {
                    if(txt_str[0].equals(province_str[i]))
                    {
                        per[i][0]+=int1;
                        per[0][0]+=int1;
                        break;
                    }
                }
            }
            else if (line.matches(str2))//疑似患者增加
            {
                String[] txt_str=line.split(" ");
                txt_str[3]=txt_str[3].replace("人","");
                int int2;
                int2=Integer.valueOf(txt_str[3]);
                for(int i=0;i<province_str.length;i++)
                {
                    if (txt_str[0].equals(province_str[i])) {
                        per[i][1] += int2;
                        per[0][1] += int2;
                        break;
                    }
                }
            }
            else if (line.matches(str3))//感染患者从省1流入省2
            {
                String[] txt_str=line.split(" ");
                txt_str[4]=txt_str[4].replace("人","");
                int int3;
                int3=Integer.valueOf(txt_str[4]);
                for(int i=0;i<province_str.length;i++)
                {
                    if (txt_str[3].equals(province_str[i])) {//省2
                        per[i][0] += int3;
                        break;
                    }
                }
                for(int j=0;j<province_str.length;j++)
                {
                    if(txt_str[0].equals(province_str[j])){//省1
                        per[j][0]-=int3;
                        break;
                    }
                }
            }
            else if(line.matches(str4))//疑似患者从省1流入省2
            {
                String[] txt_str=line.split(" ");
                txt_str[4]=txt_str[4].replace("人","");
                int int4;
                int4=Integer.valueOf(txt_str[4]);
                for(int i=0;i<province_str.length;i++)//省2
                {
                    if (txt_str[3].equals(province_str[i])) {
                        per[i][1] += int4;
                        break;
                    }
                }
                for(int j=0;j<province_str.length;j++)//省1
                {
                    if(txt_str[0].equals(province_str[j])){
                        per[j][1]-=int4;
                        break;
                    }
                }
            }
            else if (line.matches(str5))//死亡人数
            {
                String[] txt_str=line.split(" ");
                txt_str[2]=txt_str[2].replace("人","");
                int int5;
                int5=Integer.valueOf(txt_str[2]);
                for(int i=0;i<province_str.length;i++)
                {
                    if(txt_str[0].equals(province_str[i]))
                    {
                        per[i][3]+=int5;//该省死亡人数增加
                        per[i][0]-=int5;//该省感染人数减少
                        per[0][3]+=int5;//全国死亡人数增加
                        per[0][0]-=int5;//全国感染人数减少
                        break;
                    }
                }
            }
            else if(line.matches(str6))//治愈人数
            {
                String[] txt_str=line.split(" ");
                txt_str[2]=txt_str[2].replace("人","");
                int int6;
                int6=Integer.valueOf(txt_str[2]);
                for(int i=0;i<province_str.length;i++)
                {
                    if(txt_str[0].equals(province_str[i]))
                    {
                        per[i][2]+=int6;//该省治愈人数增加
                        per[i][0]-=int6;//该省感染人数减少
                        per[0][2]+=int6;//全国治愈人数增加
                        per[0][0]-=int6;//全国感染人数减少
                        break;
                    }
                }
            }
            else if (line.matches(str7))//疑似患者确诊感染
            {
                String[] txt_str=line.split(" ");
                txt_str[3]=txt_str[3].replace("人","");
                int int7;
                int7=Integer.valueOf(txt_str[3]);
                for(int i=0;i<province_str.length;i++)
                {
                    if(txt_str[0].equals(province_str[i]))
                    {
                        per[i][0]+=int7;//该省感染人数增加
                        per[i][1]-=int7;//该省疑似人数减少
                        per[0][0]+=int7;//全国感染人数增加
                        per[0][1]-=int7;//全国疑似人数减少
                        break;
                    }
                }
            }
            else if (line.matches(str8))//排除疑似患者
            {
                String[] txt_str=line.split(" ");
                txt_str[3]=txt_str[3].replace("人","");
                int int8;
                int8=Integer.valueOf(txt_str[3]);
                for(int i=0;i<province_str.length;i++)
                {
                    if(txt_str[0].equals(province_str[i]))
                    {
                        per[i][2]+=int8;//该省治愈人数增加
                        per[i][1]-=int8;//该省疑似人数减少
                        per[0][2]+=int8;//全国治愈人数增加
                        per[0][1]-=int8;//全国疑似人数减少
                        break;
                    }
                }
            }
        }

        /**
         *  输出文本
         */
        public void write_txt()
        {
            FileWriter file_write;
            try{
                file_write=new FileWriter(outpath);
                if(province[0]==0)
                    province[0]=1;
                for(int i=0;i<province_str.length;i++)
                {
                    if(province[i]==1)
                    {
                        file_write.write(province_str[i]+" ");
                        for(int j=0;j<type.length;j++)
                        {
                            if(type[j]==1)
                            {
                                file_write.write(type_str[j]+per[i][j]+"人 ");
                            }
                        }
                        file_write.write("\n");
                    }
                }
                file_write.write("// 该文档并非真实数据，仅供测试使用");
                file_write.close();
            }catch(Exception ioe)
            {
                ioe.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {

        InfectStatistic infectStatistic=new InfectStatistic();
        InfectStatistic.cmd cmd=infectStatistic.new cmd(args);
        boolean cmdE=cmd.cmd_e();
        InfectStatistic.file_handle fileHandle=infectStatistic.new file_handle();
        fileHandle.get_list();
        fileHandle.write_txt();


    }
}
