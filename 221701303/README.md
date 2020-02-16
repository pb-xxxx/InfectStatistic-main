# InfectStatistic-221701303
疫情统计

### 一、如何运行

> 1. 在cmd中打开存放该代码文件的src文件夹
> 2. 输入命令行 javac -encoding utf-8 xxx.java 编译Java文件
> 3. 输入命令行Java xxx list命令，运行代码
> 4. 由于编码原因，编译Java文件需要特殊输入utf-8

### 二、功能简介

> 通过每日提交的日志文件处理，能够列出全国和各省在某日的感染情况
>
> 命令行（win+r cmd）cd到项目src下，之后输入命令：
>
> `$ java InfectStatistic list -date 2020-01-22 -log D:/log/ -out D:/output.txt`
>
> 会读取D:/log/下的所有日志，然后处理日志和命令，在D盘下生成ouput.txt文件列出2020-01-22全国和所有省的情况（全国总是排第一个，别的省按拼音先后排序）
>
> `全国 感染患者22人 疑似患者25人 治愈10人 死亡2人 `
>
> `福建 感染患者2人 疑似患者5人 治愈0人 死亡0人 `
>
> `浙江 感染患者3人 疑似患者5人 治愈2人 死亡1人`
>
> ` // 该文档并非真实数据，仅供测试使用`
>
> list命令 支持以下命令行参数：
>
> - `-log` 指定日志目录的位置，该项**必会附带**，请直接使用传入的路径，而不是自己设置路径
> - `-out` 指定输出文件路径和文件名，该项**必会附带**，请直接使用传入的路径，而不是自己设置路径
> - `-date` 指定日期，不设置则默认为所提供日志最新的一天。你需要确保你处理了指定日期之前的所有log文件
> - `-type` 可选择[ip： infection patients 感染患者，sp： suspected patients 疑似患者，cure：治愈 ，dead：死亡患者]，使用缩写选择，如 `-type ip` 表示只列出感染患者的情况，`-type sp cure`则会按顺序【sp, cure】列出疑似患者和治愈患者的情况，不指定该项默认会列出所有情况。
> - `-province` 指定列出的省，如`-province 福建`，则只列出福建，`-province 全国 浙江`则只会列出全国、浙江
>
> 注：java InfectStatistic表示执行主类InfectStatistic，list为**命令**，-date代表该命令附带的**参数**，-date后边跟着具体的**参数值**，如`2020-01-22`。-type 的多个参数值会用空格分离，每个命令参数都在上方给出了描述，每个命令都会携带一到多个命令参数。

### 三、作业链接

作业链接：[寒假作业（2/2）](https://edu.cnblogs.com/campus/fzu/2020SpringW/homework/10281)

### 四、博客链接

博客链接：[疫情统计](https://github.com/pb-xxxx/InfectStatistic-main)

