package com.bug.framework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: 关键字检索器, 用来检索文本中是否存在敏感词
 */
@SuppressWarnings("unchecked")
public class KeyWordSearcher {

    /**
     * 敏感词库文件, 默认为totalSensitiveWords.txt
     */
    private static final String keyWordsFile = "totalKeyWords.txt";

    /**
     * 敏感词树状结构的默认大小
     */
    private static final int keyWordMapSize = 2000;

    /**
     * 敏感词树状结构
     */
    private static HashMap<Object, Object> keyWordTree;

    /**
     * 敏感词分隔符, 一部分敏感词的前缀也是敏感词, 故添加分隔符以作区分
     */
    private static final String at = "@";


    /**
     * 读取敏感词库文件, 并转为Set集合
     */
    private static HashSet<String> readKeyWordsFile() {
        // 敏感词去重 (敏感词库本身就不太可能会重复,这里是保险)
        HashSet<String> set = new HashSet<>();
        try {
            InputStream is = KeyWordSearcher.class.getClassLoader().getResourceAsStream(keyWordsFile);
            assert is != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String text;
            while ((text = reader.readLine()) != null) {
                set.add(text);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return set;
    }

    /**
     * 初始化敏感词库
     */
    private static void initSensitiveWords() {
        // 初始化敏感词树状结构
        keyWordTree = new HashMap<>(keyWordMapSize);

        // 读取敏感词库文件,并转为Set集合
        HashSet<String> keyWordSet = readKeyWordsFile();

        // 构建敏感词树状结构
        HashMap<Object, Object> map;
        HashMap<Object, Object> newMap;
        for (String keyWord : keyWordSet) {
            // 使map指向keyWordMap的内存空间
            map = keyWordTree;

            // 拆分关键词为一个个字
            for (int i = 0; i < keyWord.length(); i++) {
                char keyChar = keyWord.charAt(i);
                //判断关键词树状结构中是否存在key
                Object value = map.get(keyChar);
                if (null != value) {
                    // 已存在
                    map = (HashMap<Object, Object>) value;
                } else {
                    // 不存在
                    newMap = new HashMap<>();
                    // 标注此位不是结尾
                    newMap.put("isEnd", false);
                    // 将此位存入Map集合的值中
                    map.put(keyChar, newMap);
                    // 指向下一分支(此位的值)
                    map = newMap;
                }
                // 关键词的末尾
                if (i == keyWord.length() - 1) {
                    map.put("isEnd", true);
                }
            }
        }
    }

    /**
     * 格式化文本, 即去除文本中的特殊字符
     */
    @SuppressWarnings("RegExpDuplicateCharacterInClass")
    public static String formatText(String text) {
        //可以在中括号内加上任何想要替换的字符
        String regEx = "[\n`~!@#$%^&*()-_—+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
        // 获取正则表达式对象
        Pattern pattern = Pattern.compile(regEx);
        // 匹配字符串中的特殊字符
        Matcher matcher = pattern.matcher(text);
        // 替换字符串中的特殊字符, 并返回无特殊符号的字符串
        return matcher.replaceAll("").trim();
    }

    /**
     * 删除buffer中的敏感词分隔符
     */
    private static StringBuffer removeAt(StringBuffer buffer) {
        int index;
        while ((index = buffer.indexOf(at)) >= 0) {
            buffer.deleteCharAt(index);
        }
        return buffer;
    }

    /**
     * 检索文本中的敏感词
     */
    public static List<String> retrieve(String text) {
        // 初始化敏感词库
        initSensitiveWords();
        // 去除文本中的特殊字符
        String txt = formatText(text);

        // 检索文本中的敏感词
        List<String> list = new ArrayList<>();
        StringBuffer sb = new StringBuffer();

        Map<Object, Object> map = keyWordTree;
        for (int i = 0; i < txt.length(); i++) {
            char key = txt.charAt(i);
            Object value = map.get(key);
            if (value == null) {
                if (!sb.toString().isBlank()) {
                    // 前缀不存在, 判断是否清空buffer, buffer中有元素是 @ ,证明buffer存储的是一个敏感词
                    if (sb.indexOf(at) >= 0) {
                        list.add(removeAt(sb).toString());
                    }
                    // 保证这个value=null的key不被遗漏, 不仅要放到这个分支中检索, 还要放到整个树中检索
                    i--;
                    sb.delete(0, sb.length());
                    // 重置map对象, 使其指向敏感词库, 即指向keyWordTree
                    map = keyWordTree;
                }
            } else {// 前缀存在
                sb.append(key);
                map = (HashMap<Object, Object>) value;
                boolean isEnd = (boolean) map.get("isEnd");
                if (isEnd) {
                    sb.append(at);
                }
                if (map.size() == 1 && sb.indexOf(at) >= 0) {
                    // 是敏感词树的叶子节点, 即除了isEnd=true外, 没有其他节点
                    // 将buffer中的@去除, 然后将敏感词加入收集器中
                    list.add(removeAt(sb).toString());
                    // 清空buffer
                    sb.delete(0, sb.length());
                    // 重置map对象, 使其指向敏感词库, 即指向keyWordTree
                    map = keyWordTree;
                }
            }
        }
        if (sb.indexOf(at) >= 0) {
            list.add(removeAt(sb).toString());
        }

        return list;
    }

}
