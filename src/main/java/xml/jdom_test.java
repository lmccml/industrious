package xml;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
Java特定的文档对象模型。自身不包含解析器，使用SAX。

优点：
1、使用具体类而不是接口，简化了DOM的API。
2、大量使用了Java集合类，方便了Java开发人员。

缺点：
1、没有较好的灵活性。
2、性能较差
 */
public class jdom_test {
    private static ArrayList<book> booksList = new ArrayList<book>();
    /**
     * @param args
     */
    public static void main(String[] args) {
        // 进行对books.xml文件的JDOM解析
        // 准备工作
        // 1.创建一个SAXBuilder的对象
        SAXBuilder saxBuilder = new SAXBuilder();
        InputStream in;
        try {
            // 2.创建一个输入流，将xml文件加载到输入流中
            in = new FileInputStream(jdom_test.class.getResource("").getPath() + "books.xml");
            InputStreamReader isr = new InputStreamReader(in, "UTF-8");
            // 3.通过saxBuilder的build方法，将输入流加载到saxBuilder中
            Document document = saxBuilder.build(isr);
            // 4.通过document对象获取xml文件的根节点
            Element rootElement = document.getRootElement();
            // 5.获取根节点下的子节点的List集合
            List<Element> bookList = rootElement.getChildren();
            // 继续进行解析
            for (Element book : bookList) {
                book bookEntity = new book();
                System.out.println("======开始解析第" + (bookList.indexOf(book) + 1)
                        + "书======");
                // 解析book的属性集合
                List<Attribute> attrList = book.getAttributes();
                // //知道节点下属性名称时，获取节点值
                // book.getAttributeValue("id");
                // 遍历attrList(针对不清楚book节点下属性的名字及数量)
                for (Attribute attr : attrList) {
                    // 获取属性名
                    String attrName = attr.getName();
                    // 获取属性值
                    String attrValue = attr.getValue();
                    System.out.println("属性名：" + attrName + "----属性值："
                            + attrValue);
                    if (attrName.equals("id")) {
                        bookEntity.setId(attrValue);
                    }
                }
                // 对book节点的子节点的节点名以及节点值的遍历
                List<Element> bookChilds = book.getChildren();
                for (Element child : bookChilds) {
                    System.out.println("节点名：" + child.getName() + "----节点值："
                            + child.getValue());
                    if (child.getName().equals("name")) {
                        bookEntity.setName(child.getValue());
                    }
                    else if (child.getName().equals("author")) {
                        bookEntity.setAuthor(child.getValue());
                    }
                    else if (child.getName().equals("year")) {
                        bookEntity.setYear(child.getValue());
                    }
                    else if (child.getName().equals("price")) {
                        bookEntity.setPrice(child.getValue());
                    }
                    else if (child.getName().equals("language")) {
                        bookEntity.setLanguage(child.getValue());
                    }
                }
                System.out.println("======结束解析第" + (bookList.indexOf(book) + 1)
                        + "书======");
                booksList.add(bookEntity);
                bookEntity = null;
                System.out.println(booksList.size());
                System.out.println(booksList.get(0).getId());
                System.out.println(booksList.get(0).getName());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
