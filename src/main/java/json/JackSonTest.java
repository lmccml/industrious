package json;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JackSonTest {
    public static void main(String[] args) throws Exception {
        /*简单数据绑定就是将json字符串映射为java核心的数据类型。
        json类型	Java类型
        object	LinkedHashMap
        array	ArrayList
        string	String
        number	Integer,Long,Double
        true|false	Boolean
        null	null
        */
        String s = "{\"id\": 1,\"name\": \"小明\",\"array\": [\"1\", \"2\"]}";
        ObjectMapper mapper = new ObjectMapper();
        //Json映射为对象
        Student student = mapper.readValue(s, Student.class);
        //对象转化为Json
        String json = mapper.writeValueAsString(student);
        System.out.println(json);
        System.out.println(student.getId());
        System.out.println(student.toString());

        /**
         * 简单数据绑定的示例，不用POJO对象，直接映射为一个Map，然后从Map中获取。
         */
        Map<String, Object> map = new HashMap<>(16);
        String str = "{\"id\": 1,\"name\": \"小明\",\"array\": [\"1\", \"2\"]," +
                "\"test\":\"I'm test\",\"base\": {\"major\": \"物联网\",\"class\": \"3\"}}";
        ObjectMapper mapper2 = new ObjectMapper();
        map = mapper2.readValue(str, map.getClass());
        //获取id
        Integer studentId = (Integer) map.get("id");
        System.out.println(studentId);
        //获取数据
        ArrayList list = (ArrayList) map.get("array");
        System.out.println(Arrays.toString(list.toArray()));
        //新增加的字段可以很方便的处理
        String test = (String) map.get("test");
        System.out.println(test);
        //不存在的返回null
        String notExist = (String) map.get("notExist");
        System.out.println(notExist);
        //嵌套的对象获取
        Map base = (Map) map.get("base");
        String major = (String) base.get("major");
        System.out.println(major);

        /**
         * JackSon树模型结构，可以通过get，JsonPointer等进行操作，适合用来获取大Json中的字段，比较灵活。缺点是如果需要获取的内容较多，
         * 会显得比较繁琐。
         */
        ObjectMapper mapper3 = new ObjectMapper();
        //以下是对象转化为Json
        JsonNode root = mapper.createObjectNode();
        ((ObjectNode) root).putArray("array");
        ArrayNode arrayNode = (ArrayNode) root.get("array");
        ((ArrayNode) arrayNode).add("args1");
        ((ArrayNode) arrayNode).add("args2");
        ((ObjectNode) root).put("name", "小红");
        String json2 = mapper3.writeValueAsString(root);
        System.out.println("使用树型模型构建的json:" + json2);
        //以下是树模型的解析Json
        String ss = "{\"id\": 1,\"name\": \"小明\",\"array\": [\"1\", \"2\"]," +
                "\"test\":\"I'm test\",\"nullNode\":null,\"base\": {\"major\": \"物联网\",\"class\": \"3\"}}";
        //读取rootNode
        JsonNode rootNode = mapper3.readTree(ss);
        //通过path获取
        System.out.println("通过path获取值：" + rootNode.path("name").asText());
        //通过JsonPointer可以直接按照路径获取
        JsonPointer pointer = JsonPointer.valueOf("/base/major");
        JsonNode node = rootNode.at(pointer);
        System.out.println("通过at获取值:" + node.asText());
        //通过get可以取对应的value
        JsonNode classNode = rootNode.get("base");
        System.out.println("通过get获取值：" + classNode.get("major").asText());

        //获取数组的值
        System.out.print("获取数组的值：");
        JsonNode arrayNode2 = rootNode.get("array");
        for (int i = 0; i < arrayNode2.size(); i++) {
            System.out.print(arrayNode2.get(i).asText() + " ");
        }
        System.out.println();
        //path和get方法看起来很相似，其实他们的细节不同，get方法取不存在的值的时候，会返回null。而path方法会
        //返回一个"missing node"，该"missing node"的isMissingNode方法返回值为true，如果调用该node的asText方法的话，
        // 结果是一个空字符串。
        System.out.println("get方法取不存在的节点，返回null:" + (rootNode.get("notExist") == null));
        JsonNode notExistNode = rootNode.path("notExist");
        System.out.println("notExistNode的value：" + notExistNode.asText());
        System.out.println("isMissingNode方法返回true:" + notExistNode.isMissingNode());

        //当key存在，而value为null的时候，get和path都会返回一个NullNode节点。
        System.out.println(rootNode.get("nullNode") instanceof NullNode);
        System.out.println(rootNode.path("nullNode") instanceof NullNode);


        /**
         *  JsonParser和Generator的优点是速度快，缺点是写起来真的很复杂。
         */
        JsonFactory factory = new JsonFactory();
        String sss = "{\"id\": 1,\"name\": \"小明\",\"array\": [\"1\", \"2\"]," +
                "\"test\":\"I'm test\",\"nullNode\":null,\"base\": {\"major\": \"物联网\",\"class\": \"3\"}}";

        //这里就举一个比较简单的例子，Generator的用法就是一个一个write即可。
        String project_path = System.getProperty("user.dir");
        File file = new File(project_path + "/file/json.txt");
        JsonGenerator jsonGenerator = factory.createGenerator(file, JsonEncoding.UTF8);
        //对象开始
        jsonGenerator.writeStartObject();
        //写入一个键值对
        jsonGenerator.writeStringField("name", "小光");
        //对象结束
        jsonGenerator.writeEndObject();
        //关闭jsonGenerator
        jsonGenerator.close();
        //读取刚刚写入的json
        FileInputStream inputStream = new FileInputStream(file);
        int i = 0;
        final int SIZE = 1024;
        byte[] buf = new byte[SIZE];
        StringBuilder sb = new StringBuilder();
        while ((i = inputStream.read(buf)) != -1) {
            System.out.println(new String(buf,0,i));
        }
        inputStream.close();


        //JsonParser解析的时候，思路是把json字符串根据边界符分割为若干个JsonToken，这个JsonToken是一个枚举类型。
        //下面这个小例子，可以看出JsonToken是如何划分类型的。
        JsonParser parser = factory.createParser(s);
        while (!parser.isClosed()){
            JsonToken token = parser.currentToken();
            System.out.println(token);
            parser.nextToken();
        }

        JsonParser jsonParser = factory.createParser(s);
        //下面是一个解析的实例
        while (!jsonParser.isClosed()) {
            JsonToken token  = jsonParser.nextToken();
            if (JsonToken.FIELD_NAME.equals(token)) {
                String currentName = jsonParser.currentName();
                token = jsonParser.nextToken();
                if ("id".equals(currentName)) {
                    System.out.println("id:" + jsonParser.getValueAsInt());
                } else if ("name".equals(currentName)) {
                    System.out.println("name:" + jsonParser.getValueAsString());
                } else if ("array".equals(currentName)) {
                    token = jsonParser.nextToken();
                    while (!JsonToken.END_ARRAY.equals(token)) {
                        System.out.println("array:" + jsonParser.getValueAsString());
                        token = jsonParser.nextToken();
                    }
                }
            }
        }
    }

}
