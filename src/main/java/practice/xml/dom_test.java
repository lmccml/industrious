package practice.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/*
优点：
1. 允许应用程序对数据和结构做出更改。
2. 访问是双向的，可以在任何时候再树中上、下导航获取、操作任意部分的数据。
缺点：
1. 解析XML文档的需要加载整个文档来构造层次结构，消耗内存资源大。
应用范围：
由于他的遍历能力，DOM解析器常应用于XML文档需要频繁改变的服务中。

Java使用步骤：
1. 创建一个DocumentBuilderFactory对象。
2. 创建一个DocumentBuilder对象。
3. 通过DocumentBuilder的parse方法加载XML到当前工程目录下。
4. 通过getElementsByTagName方法获取所有XML所有节点的集合。
5. 遍历所有节点。
6. 通过item方法获取某个节点的属性。
7. 通过getNodeName和getNodeValue方法获取属性名和属性值。
8. 通过getChildNodes方法获取子节点，并遍历所有子节点。
9. 通过getNodeName和getTextContent方法获取子节点名称和子节点值。

缺点：
1、通常需要加载整个XML文档来构造层次结构，消耗资源大
 */
public class dom_test {
    public static void main(String[] args) {
        //1.创建一个DocumentBuilderFactory对象
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            //2.创建一个DocumentBuilder对象
            DocumentBuilder db = dbf.newDocumentBuilder();

            //3.通过DocumentBuilder的parse方法加载books.xml到当前工程目录下
            Document document = db.parse(dom_test.class.getResource("") + "books.xml");

            //4.获取所有book所有节点的集合
            NodeList bookList = document.getElementsByTagName("book");

            //通过NodeList的getLength（）方法获取 bookList的长度
            int bookCnt = bookList.getLength();
            System.out.println("获取了" + bookCnt +"本书");

            for(int i=0; i<bookCnt; i++){
                //5.通过item方法获取一个book节点，bookList索引值从0开始
                Node book = bookList.item(i);

                //6.获取book节点的所有属性集合
                NamedNodeMap attrs = book.getAttributes();

                System.out.println("第"+ (i+1) +"本书共有"+ attrs.getLength()+"个属性");
                for(int j=0; j<attrs.getLength(); j++){
                    //7.通过这个item()方法获取book某一个属性
                    Node attr = attrs.item(j);

                    //8.获取属性名
                    System.out.print("属性名：" + attr.getNodeName()+"----");

                    //9.获取属性值
                    System.out.println("属性值：" + attr.getNodeValue());
                }

                //10.通过getChildNodes()方法解析book节点子节点
                NodeList childNodes = book.getChildNodes();

                //11.遍历childNodes获取每隔节点的节点名和节点值
                for(int k=0; k<childNodes.getLength(); k++){
                    //12.区分出text类型和element类型的node
                    if(childNodes.item(k).getNodeType() == Node.ELEMENT_NODE){
                        //13.获取element类型节点的节点名
                        System.out.print("第"+ (k+1) + "节点的节点名："+childNodes.item(k).getNodeName()+"  ");

                        //14.获取element类型节点的节点值
                        System.out.println("节点值：" + childNodes.item(k).getTextContent());
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


