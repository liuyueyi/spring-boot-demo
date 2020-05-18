package com.git.hui.boot.spel.demo;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 基本演示
 * Created by @author yihui in 14:30 20/5/14.
 */
@Component
public class BasicSpelDemo implements ApplicationContextAware {


    public void test() {
        literaExpression();
        list();
        map();
        array();
        expression();
        type();
        construct();
        variable();
        function();
        bean();
        ifThenElse();
        elvis();
        safeOperate();
        collectionSelection();
        collectionProjection();
        template();
    }

    public void literaExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        // evals to "Hello World"
        String helloWorld = (String) parser.parseExpression("'Hello World'").getValue();
        System.out.println("str: " + helloWorld);
        // double 类型
        double avogadrosNumber = (Double) parser.parseExpression("6.0221415E+23").getValue();
        System.out.println("double: " + avogadrosNumber);

        // evals to 2147483647
        int maxValue = (Integer) parser.parseExpression("0x7FFFFFFF").getValue();
        System.out.println("int: " + maxValue);

        boolean trueValue = (Boolean) parser.parseExpression("true").getValue();
        System.out.println("bool: " + trueValue);

        Object nullValue = parser.parseExpression("null").getValue();
        System.out.println("null: " + nullValue);
    }

    private void list() {
        ExpressionParser parser = new SpelExpressionParser();
        List numbers = (List) parser.parseExpression("{1,2,3,4}").getValue();
        System.out.println("list: " + numbers);

        List listlOfLists = (List) parser.parseExpression("{{'a','b'},{'x','y'}}").getValue();
        System.out.println("List<List> : " + listlOfLists);
    }

    private void map() {
        ExpressionParser parser = new SpelExpressionParser();
        Map map = (Map) parser.parseExpression("{txt:'Nikola',dob:'10-July-1856'}").getValue();
        System.out.println("map: " + map);
        Map mapOfMaps =
                (Map) parser.parseExpression("{txt:{first:'Nikola',last:'Tesla'},dob:{day:10,month:'July',year:1856}}")
                        .getValue();
        System.out.println("Map<Map>: " + mapOfMaps);
    }

    private void array() {
        ExpressionParser parser = new SpelExpressionParser();
        int[] numbers1 = (int[]) parser.parseExpression("new int[4]").getValue();
        System.out.println("array: " + JSON.toJSONString(numbers1));

        // Array with initializer
        int[] numbers2 = (int[]) parser.parseExpression("new int[]{1,2,3}").getValue();
        System.out.println("array: " + JSON.toJSONString(numbers2));

        // Multi dimensional array
        int[][] numbers3 = (int[][]) parser.parseExpression("new int[4][5]").getValue();
        System.out.println("array: " + JSON.toJSONString(numbers3));


        int[] nums = new int[]{1, 3, 5};
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("num", nums);

        Integer numVal = parser.parseExpression("#num[1]").getValue(context, Integer.class);
        System.out.println("numVal in array: " + numVal);
    }

    public void expression() {
        ExpressionParser parser = new SpelExpressionParser();
        // 运算
        System.out.println("1+2= " + parser.parseExpression("1+2").getValue());
        // 比较
        System.out.println("1<2= " + parser.parseExpression("1<2").getValue());
        System.out.println("true ? hello : false > " + parser.parseExpression("3 > 2 ? 'hello': 'false' ").getValue());
        // instanceof 判断，请注意静态类，用T进行包装
        System.out.println("instance : " + parser.parseExpression("'a' instanceof T(String)").getValue());
        //正则表达式
        System.out.println("22 是否为两位数字 :" + parser.parseExpression("22 matches '\\d{2}'").getValue());
    }


    public static class StaClz {
        public static String txt = "静态属性";

        public static String hello(String tag) {
            return txt + " : " + tag;
        }
    }

    public void type() {
        // class，静态类
        ExpressionParser parser = new SpelExpressionParser();
        String name =
                parser.parseExpression("T(com.git.hui.boot.spel.demo.BasicSpelDemo.StaClz).txt").getValue(String.class);
        System.out.println("txt: " + name);

        String methodReturn =
                parser.parseExpression("T(com.git.hui.boot.spel.demo.BasicSpelDemo.StaClz).hello" + "('一灰灰blog')")
                        .getValue(String.class);
        System.out.println("static method return: " + methodReturn);

        // class类获取
        Class stringClass = parser.parseExpression("T(String)").getValue(Class.class);
        System.out.println("class: " + stringClass.getName());
    }


    public static class Person {
        private String name;

        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Person{" + "txt='" + name + '\'' + ", age=" + age + '}';
        }
    }


    public void construct() {
        ExpressionParser parser = new SpelExpressionParser();
        Person person = parser.parseExpression("new com.git.hui.boot.spel.demo.BasicSpelDemo.Person('一灰灰', 20)")
                .getValue(Person.class);
        System.out.println("person: " + person);
    }


    public void variable() {
        ExpressionParser parser = new SpelExpressionParser();
        Person person = new Person("一灰灰blog", 18);
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("person", person);

        String name = parser.parseExpression("#person.getName()").getValue(context, String.class);
        System.out.println("variable name: " + name);

        Integer age = parser.parseExpression("#person.age").getValue(context, Integer.class);
        System.out.println("variable age: " + age);
    }

    public void function() {
        try {
            ExpressionParser parser = new SpelExpressionParser();
            EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
            // 注册一个方法变量，参数为method类型
            context.setVariable("hello", StaClz.class.getDeclaredMethod("hello", String.class));

            String ans = parser.parseExpression("#hello('一灰灰')").getValue(context, String.class);
            System.out.println("function call: " + ans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public void bean() {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(applicationContext));

        // 获取bean对象
        BeanDemo beanDemo = parser.parseExpression("@beanDemo").getValue(context, BeanDemo.class);
        System.out.println("bean: " + beanDemo);

        // 访问bean方法
        String ans = parser.parseExpression("@beanDemo.hello('一灰灰blog')").getValue(context, String.class);
        System.out.println("bean method return: " + ans);
    }


    public void ifThenElse() {
        // 三元表达式，? :
        ExpressionParser parser = new SpelExpressionParser();
        String ans = parser.parseExpression("true ? '正确': '错误'").getValue(String.class);
        System.out.println("ifTheElse: " + ans);
    }

    public void elvis() {
        // xx != null ? xx : yy => xx?:yy
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("name", null);
        String name = parser.parseExpression("#name?:'Unknown'").getValue(context, String.class);
        System.out.println("elvis-before " + name);

        context.setVariable("name", "Exists!");
        name = parser.parseExpression("#name?:'Unknown'").getValue(context, String.class);
        System.out.println("elvis-after " + name);
    }

    public void safeOperate() {
        // 防npe写法, xx == null ? null : xx.yy  => xx?.yy
        ExpressionParser parser = new SpelExpressionParser();
        Person person = new Person(null, 18);

        String name = parser.parseExpression("name?.length()").getValue(person, String.class);
        System.out.println("safeOperate-before: " + name);

        person.name = "一灰灰blog";
        name = parser.parseExpression("name?.length()").getValue(person, String.class);
        System.out.println("safeOperate-after: " + name);
    }


    public void collectionSelection() {
        // 容器截取，返回满足条件的子集
        // xx.?[expression] , 将满足expression的子元素保留，返回一个新的集合，类似容器的 filter
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 3, 4, 6, 7, 8, 9));
        ExpressionParser parser = new SpelExpressionParser();

        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        context.setVariable("list", list);
        // 用 #this 来指代列表中的迭代元素
        List<Integer> subList = (List<Integer>) parser.parseExpression("#list.?[#this>5]").getValue(context);
        System.out.println("subList: " + subList);


        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 10);
        map.put("c", 4);
        map.put("d", 7);
        context.setVariable("map", map);
        // 表达式内部用key, value 来指代map的k,v
        Map subMap = parser.parseExpression("#map.?[value < 5]").getValue(context, Map.class);
        System.out.println("subMap: " + subMap);

        subMap = parser.parseExpression("#map.?[key == 'a']").getValue(context, Map.class);
        System.out.println("subMap: " + subMap);
    }


    public void collectionProjection() {
        // 容器操作之后，生成另一个容器, 类似lambda中的map方法
        // xx.![expression]

        List<Integer> list = new ArrayList<>(Arrays.asList(1, 3, 4, 6, 7, 8, 9));
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        context.setVariable("list", list);

        // 用 #this 来指代列表中的迭代元素
        List newList = parser.parseExpression("#list.![#this * 2]").getValue(context, List.class);
        System.out.println("newList: " + newList);


        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 10);
        map.put("c", 4);
        map.put("d", 7);
        context.setVariable("map", map);
        List newListByMap = parser.parseExpression("#map.![value * 2]").getValue(context, List.class);
        System.out.println("newListByMap: " + newListByMap);
    }

    public void template() {
        // 模板，混合字面文本与表达式，使用 #{} 将表达式包裹起来
        ExpressionParser parser = new SpelExpressionParser();
        String randomPhrase = parser.parseExpression("random number is #{T(java.lang.Math).random()}",
                ParserContext.TEMPLATE_EXPRESSION).getValue(String.class);
        System.out.println("template: " + randomPhrase);

    }

}
