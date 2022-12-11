package Controller;

import Model.HeaderModel;
import Model.KeywordModel;
import Model.DatatypeModel;

import java.util.*;
import java.awt.*;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.util.Stack; //import
public class InputController implements Runnable {
    private final JTextPane UserText;
    private String UserTextString;
    private char[] UserTextCharacter;
    private String[] UserTextCharacterColor;
    private ArrayList<String> UserTextWord;

    private final String[] getKeyword;
    private final String[] getHeader;
    private final String[] getDatatype;

    private final AttributeSet CyanColor;
    private final AttributeSet BlackColor;
    private final AttributeSet PinkColor;
    private final AttributeSet RedColor;
    private final AttributeSet ErrorColor;
    private final AttributeSet MagentaColor;
    private final AttributeSet BlueColor;
    private final AttributeSet GreenColor;
    private final AttributeSet OrangeColor;
    private final AttributeSet GrayColor;
    public InputController(JTextPane text) {
        HeaderModel Header = new HeaderModel();
        DatatypeModel Datatype = new DatatypeModel();
        KeywordModel KeyWord = new KeywordModel();
        getKeyword = KeyWord.getKeyword();
        getHeader = Header.getHeader();
        getDatatype = Datatype.getDatatype();
        this.UserText = text;
        new Thread(this).start();
        StyleContext context = new StyleContext();
        PinkColor = context.addAttribute(SimpleAttributeSet.EMPTY,StyleConstants.Foreground, Color.PINK);
        CyanColor = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.CYAN);
        BlackColor = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);
        MagentaColor = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.MAGENTA);
        BlueColor = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLUE);
        RedColor = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.RED);
        GreenColor = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.GREEN);
        OrangeColor = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.ORANGE);
        GrayColor=context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.GRAY);
        AttributeSet as_underline = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Underline, Boolean.TRUE);
        ErrorColor = context.addAttributes(RedColor, as_underline);

    }

    @Override
    public void run() {
        while (true) {
            UserTextString = UserText.getText().replace("\r\n", "\n");
            StyledDocument userTextDocument = UserText.getStyledDocument();
            UserTextCharacter = UserTextString.toCharArray();
            UserTextCharacterColor = new String[UserTextCharacter.length];
            Arrays.fill(UserTextCharacterColor, "");
            UserTextWord = new ArrayList<String>();
            StringTokenizer userTextTokenizer = new StringTokenizer(UserTextString, "\t|\r|\n|\u0020|\u00A0");
            while (userTextTokenizer.hasMoreElements()) {
                UserTextWord.add(userTextTokenizer.nextToken());
            }
            KeywordEdit();
            IncludeEdit();
            DefineEdit();
            DateTypeEdit();
            MarkEdit();
            for (int ColorSetindex = 0; ColorSetindex < UserTextCharacter.length; ColorSetindex++) {
                if (UserTextCharacterColor[ColorSetindex].equals("CYAN"))
                    userTextDocument.setCharacterAttributes(ColorSetindex, 1, CyanColor, true);
                else if (UserTextCharacterColor[ColorSetindex].equals("GREEN"))
                    userTextDocument.setCharacterAttributes(ColorSetindex, 1, GreenColor, true);
                else if (UserTextCharacterColor[ColorSetindex].equals("ORANGE"))
                    userTextDocument.setCharacterAttributes(ColorSetindex, 1, OrangeColor, true);
                else if (UserTextCharacterColor[ColorSetindex].equals("MAGENTA"))
                    userTextDocument.setCharacterAttributes(ColorSetindex, 1, MagentaColor, true);
                else if (UserTextCharacterColor[ColorSetindex].equals("BLUE"))
                    userTextDocument.setCharacterAttributes(ColorSetindex, 1, BlueColor, true);
                else if (UserTextCharacterColor[ColorSetindex].equals("PINK"))
                    userTextDocument.setCharacterAttributes(ColorSetindex, 1, PinkColor, true);
                else if (UserTextCharacterColor[ColorSetindex].equals("RED"))
                    userTextDocument.setCharacterAttributes(ColorSetindex, 1, RedColor, true);
                else if (UserTextCharacterColor[ColorSetindex].equals("GRAY"))
                    userTextDocument.setCharacterAttributes(ColorSetindex, 1, GrayColor, true);
                else if (UserTextCharacterColor[ColorSetindex].equals("ERROR"))
                    userTextDocument.setCharacterAttributes(ColorSetindex, 1, ErrorColor, true);
                else
                    userTextDocument.setCharacterAttributes(ColorSetindex, 1, BlackColor, true);
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void KeywordEdit() {
        int KeyWordindex = 0;
        String KeywordValue = null;
        String ModelKeywordValue = null;
        for (int Wordindex = 0; Wordindex < UserTextWord.size(); Wordindex++) {
            for (int ModelKeywordindex = 0; ModelKeywordindex < getKeyword.length; ModelKeywordindex++) {
                KeywordValue = UserTextWord.get(Wordindex);
                ModelKeywordValue = getKeyword[ModelKeywordindex];
                if (KeywordValue.equals(ModelKeywordValue)) {
                    KeyWordindex = UserTextString.indexOf(ModelKeywordValue, KeyWordindex);
                    for (int SeachKeyWordColor = KeyWordindex; SeachKeyWordColor < KeyWordindex + ModelKeywordValue.length(); SeachKeyWordColor++) {
                        UserTextCharacterColor[SeachKeyWordColor] = "CYAN";
                    }
                    KeyWordindex = KeyWordindex + KeywordValue.length();
                } else if (KeywordValue.equals("(" + ModelKeywordValue) || KeywordValue.equals("{" + ModelKeywordValue) || KeywordValue.equals("[" + ModelKeywordValue)) {
                    KeyWordindex = UserTextString.indexOf(KeywordValue, KeyWordindex);
                    for (int SeachKeyWordColor = KeyWordindex + 1; SeachKeyWordColor < KeyWordindex + ModelKeywordValue.length() + 1; SeachKeyWordColor++) {
                        UserTextCharacterColor[SeachKeyWordColor] = "CYAN";
                    }
                    KeyWordindex = KeyWordindex + KeywordValue.length();
                } else if (KeywordValue.equals(ModelKeywordValue + ")") || KeywordValue.equals(ModelKeywordValue + "}") || KeywordValue.equals(ModelKeywordValue + "]")) {
                    KeyWordindex = UserTextString.indexOf(KeywordValue, KeyWordindex);
                    for (int SeachKeyWordColor = KeyWordindex; SeachKeyWordColor < KeyWordindex + ModelKeywordValue.length(); SeachKeyWordColor++) {
                        UserTextCharacterColor[SeachKeyWordColor] = "CYAN";
                    }
                    KeyWordindex = KeyWordindex + KeywordValue.length();
                } else if (KeywordValue.equals("(" + ModelKeywordValue + ")") || KeywordValue.equals("{" + ModelKeywordValue + "}") || KeywordValue.equals("[" + ModelKeywordValue + "]")) {
                    KeyWordindex = UserTextString.indexOf(KeywordValue, KeyWordindex);
                    for (int SeachKeyWordColor = KeyWordindex + 1; SeachKeyWordColor < KeyWordindex + ModelKeywordValue.length() + 1; SeachKeyWordColor++) {
                        UserTextCharacterColor[SeachKeyWordColor] = "CYAN";
                    }
                    KeyWordindex = KeyWordindex + KeywordValue.length();
                } else if (KeywordValue.contains(ModelKeywordValue)) {
                    KeyWordindex = UserTextString.indexOf(KeywordValue, KeyWordindex);
                    KeyWordindex = KeyWordindex + KeywordValue.length();
                }
            }
        }
    }

    public void IncludeEdit() {
        int Headerindex = 0;
        for (int WordIndex = 0; WordIndex < UserTextWord.size(); WordIndex++) {
            for (int HeaderIndex = 0; HeaderIndex < getHeader.length; HeaderIndex++) {
                if (UserTextWord.get(WordIndex).equals("#include" + getHeader[HeaderIndex])) {
                    Headerindex = UserTextString.indexOf(UserTextWord.get(WordIndex), Headerindex);
                    for (int IncludeColor = Headerindex; IncludeColor < Headerindex + "#include".length(); IncludeColor++) {
                        //System.out.println(k);
                        UserTextCharacterColor[IncludeColor] = "CYAN";
                    }
                    Headerindex = Headerindex + "#include".length();
                    for (int HeaderColor = Headerindex; HeaderColor < Headerindex + getHeader[HeaderIndex].length(); HeaderColor++) {
                        //System.out.println(k);
                        UserTextCharacterColor[HeaderColor] = "PINK";
                    }
                    Headerindex = Headerindex + getHeader[WordIndex].length();
                    //System.out.println("test1");
                } else if (UserTextWord.get(WordIndex).contains("#include" + getHeader[HeaderIndex])) {
                    Headerindex = UserTextString.indexOf(UserTextWord.get(WordIndex), Headerindex);
                    Headerindex = Headerindex + UserTextWord.get(WordIndex).length();
                    //System.out.println("test2");
                } else if (WordIndex != 0 && UserTextWord.get(WordIndex - 1).equals("#include")) {
                    if (UserTextWord.get(WordIndex).equals(getHeader[HeaderIndex])) {
                        Headerindex = UserTextString.indexOf("#include", Headerindex);
                        Headerindex = Headerindex + "#include".length();
                        Headerindex = UserTextString.indexOf(getHeader[HeaderIndex], Headerindex);
                        //System.out.println("test2"+Headerindex);
                        for (int HeaderColor = Headerindex; HeaderColor < Headerindex + getHeader[HeaderIndex].length(); HeaderColor++) {
                            //System.out.println(k);
                            UserTextCharacterColor[HeaderColor] = "PINK";
                        }
                        Headerindex = Headerindex + getHeader[WordIndex].length();
                        //System.out.println("test3");
                    } else if (UserTextWord.get(WordIndex).contains(getHeader[HeaderIndex])) {
                        Headerindex = UserTextString.indexOf(UserTextWord.get(WordIndex), Headerindex);
                        Headerindex = Headerindex + UserTextWord.get(WordIndex).length();
                        //System.out.println("test4");
                    }
                }
            }
        }
    }

    public void DefineEdit() {
        char DefinecheckFirst = ' ';
        char DefinecheckLast = ' ';
        int Defineindex = 0;
        String DefineValue = null;
        int DefineValueindex = 0;
        String SearchConstantValue = null;
        for (int Wordindex = 1; Wordindex < UserTextWord.size(); Wordindex++) {
            if (UserTextWord.get(Wordindex - 1).equals("#define")) {
                DefinecheckFirst = UserTextWord.get(Wordindex).charAt(0);
                DefinecheckLast= UserTextWord.get(Wordindex).charAt(UserTextWord.get(Wordindex).length() -1);
                DefineValue = UserTextWord.get(Wordindex);
                Defineindex = UserTextString.indexOf("#define", Defineindex);
                Defineindex = Defineindex + "#define".length();
                Defineindex = UserTextString.indexOf(DefineValue, Defineindex);
                if (!Character.isDigit(DefinecheckFirst) && DefinecheckLast != ';' && DefinecheckFirst != '!' && DefinecheckFirst != '@' && DefinecheckFirst != '#' && DefinecheckFirst != '%' && DefinecheckFirst != '^' && DefinecheckFirst != '&' && DefinecheckFirst != '*' && DefinecheckFirst != '(' && DefinecheckFirst != ')') {
                    for (int ConstantColor = Defineindex; ConstantColor < Defineindex + DefineValue.length(); ConstantColor++) {
                        UserTextCharacterColor[ConstantColor] = "MAGENTA";
                        //System.out.println("test1:     "+ConstantColor);
                    }
                    Defineindex = Defineindex + DefineValue.length();
                    DefineValueindex = Defineindex;
                    for (int SearchConstant = Wordindex + 1; SearchConstant < UserTextWord.size(); SearchConstant++) {
                        SearchConstantValue = UserTextWord.get(SearchConstant);
                        if (SearchConstantValue.equals(DefineValue)) {
                            DefineValueindex = UserTextString.indexOf(SearchConstantValue, DefineValueindex);
                            for (int SeachConstantColor = DefineValueindex; SeachConstantColor < DefineValueindex + DefineValue.length(); SeachConstantColor++) {
                                UserTextCharacterColor[SeachConstantColor] = "MAGENTA";
                                //System.out.println("test2:     "+h);
                            }
                            DefineValueindex = DefineValueindex + SearchConstantValue.length();
                        } else if (SearchConstantValue.equals("(" + DefineValue) || SearchConstantValue.equals("{" + DefineValue) || SearchConstantValue.equals("[" + DefineValue)) {
                            DefineValueindex = UserTextString.indexOf(SearchConstantValue, DefineValueindex);
                            for (int SeachConstantColor = DefineValueindex + 1; SeachConstantColor < DefineValueindex + DefineValue.length() + 1; SeachConstantColor++) {
                                UserTextCharacterColor[SeachConstantColor] = "MAGENTA";
                            }
                            DefineValueindex = DefineValueindex + SearchConstantValue.length();
                        } else if (SearchConstantValue.equals(DefineValue + ")") || SearchConstantValue.equals(DefineValue + "}") || SearchConstantValue.equals(DefineValue + "]")) {
                            DefineValueindex = UserTextString.indexOf(SearchConstantValue, DefineValueindex);
                            for (int SeachConstantColor = DefineValueindex; SeachConstantColor < DefineValueindex + DefineValue.length(); SeachConstantColor++) {
                                UserTextCharacterColor[SeachConstantColor] = "MAGENTA";
                            }
                            DefineValueindex = DefineValueindex + SearchConstantValue.length();
                        } else if (SearchConstantValue.equals("(" + DefineValue + ")") || SearchConstantValue.equals("{" + DefineValue + "}") || SearchConstantValue.equals("[" + DefineValue + "]")) {
                            DefineValueindex = UserTextString.indexOf(SearchConstantValue, DefineValueindex);
                            for (int SeachConstantColor = DefineValueindex + 1; SeachConstantColor < DefineValueindex + DefineValue.length() + 1; SeachConstantColor++) {
                                UserTextCharacterColor[SeachConstantColor] = "MAGENTA";
                            }
                            DefineValueindex = DefineValueindex + SearchConstantValue.length();
                        } else if (SearchConstantValue.contains(DefineValue)) {
                            DefineValueindex = UserTextString.indexOf(SearchConstantValue, DefineValueindex);
                            DefineValueindex = DefineValueindex + SearchConstantValue.length();
                        }
                    }
                } else {
                    for (int j = Defineindex; j < UserTextCharacter.length; j++) {
                        if (UserTextCharacter[j] == '\n') {
                            break;
                        }
                        UserTextCharacterColor[j] = "ERROR";
                    }
                    Defineindex = Defineindex + DefineValue.length();
                }
            }
        }
    }


    public void DateTypeEdit() {
        String DataValue = null;
        String ModelDataValue = null;
        int Dataindex = 0;
        for (int Wordindex = 0; Wordindex < UserTextWord.size(); Wordindex++) {
            for (int ModelDataindex = 0; ModelDataindex < getDatatype.length; ModelDataindex++) {
                DataValue = UserTextWord.get(Wordindex);
                ModelDataValue = getDatatype[ModelDataindex];
                if (DataValue.equals(ModelDataValue)) {
                    Dataindex = UserTextString.indexOf(ModelDataValue, Dataindex);
                    for (int SeachKeyWordColor = Dataindex; SeachKeyWordColor < Dataindex + ModelDataValue.length(); SeachKeyWordColor++) {
                        UserTextCharacterColor[SeachKeyWordColor] = "BLUE";
                    }
                    Dataindex = Dataindex + DataValue.length();
                } else if (DataValue.equals("(" + ModelDataValue) || DataValue.equals("{" + ModelDataValue) || DataValue.equals("[" + ModelDataValue)) {
                    Dataindex = UserTextString.indexOf(DataValue, Dataindex);
                    for (int SeachKeyWordColor = Dataindex + 1; SeachKeyWordColor < Dataindex + ModelDataValue.length() + 1; SeachKeyWordColor++) {
                        UserTextCharacterColor[SeachKeyWordColor] = "BLUE";
                    }
                    Dataindex = Dataindex + DataValue.length();
                } else if (DataValue.equals(ModelDataValue + ")") || DataValue.equals(ModelDataValue + "}") || DataValue.equals(ModelDataValue + "]")) {
                    Dataindex = UserTextString.indexOf(DataValue, Dataindex);
                    for (int SeachKeyWordColor = Dataindex; SeachKeyWordColor < Dataindex + ModelDataValue.length(); SeachKeyWordColor++) {
                        UserTextCharacterColor[SeachKeyWordColor] = "BLUE";
                    }
                    Dataindex = Dataindex + DataValue.length();
                } else if (DataValue.equals("(" + ModelDataValue + ")") || DataValue.equals("{" + ModelDataValue + "}") || DataValue.equals("[" + ModelDataValue + "]")) {
                    Dataindex = UserTextString.indexOf(DataValue, Dataindex);
                    for (int SeachKeyWordColor = Dataindex + 1; SeachKeyWordColor < Dataindex + ModelDataValue.length() + 1; SeachKeyWordColor++) {
                        UserTextCharacterColor[SeachKeyWordColor] = "BLUE";
                    }
                    Dataindex = Dataindex + DataValue.length();
                } else if (DataValue.contains(ModelDataValue)) {
                    Dataindex = UserTextString.indexOf(DataValue, Dataindex);
                    Dataindex = Dataindex + DataValue.length();
                }
            }
        }

        char DataTypecheckFirst = ' ';
        char DataTypecheckLast = ' ';
        int DataTypeindex = 0;
        String DataTypeValue = null;
        int DataTypeValueindex = 0;
        String SearchDataValue = null;
        String ModellDataTypeValue = null;
        for (int Wordindex = 1; Wordindex < UserTextWord.size(); Wordindex++) {
            if (!(Arrays.asList(getDatatype).contains(UserTextWord.get(Wordindex)))) {
                //System.out.println("test1:     " + DataTypeindex);
                if ((Arrays.asList(getDatatype).contains(UserTextWord.get(Wordindex - 1)))) {
                    ModellDataTypeValue = UserTextWord.get(Wordindex - 1);
                    DataTypecheckFirst = UserTextWord.get(Wordindex).charAt(0);
                    DataTypecheckLast= UserTextWord.get(Wordindex).charAt(UserTextWord.get(Wordindex).length() -1);
                    DataTypeValue = UserTextWord.get(Wordindex);
                    DataTypeindex = UserTextString.indexOf(ModellDataTypeValue, DataTypeindex);
                    DataTypeindex = DataTypeindex + ModellDataTypeValue.length();
                    DataTypeindex = UserTextString.indexOf(DataTypeValue, DataTypeindex);
                    //System.out.println("test2:     " + DataTypeindex);
                    if (!Character.isDigit(DataTypecheckFirst) && DataTypecheckLast != ';' && DataTypecheckFirst != '!' && DataTypecheckFirst != '@' && DataTypecheckFirst != '#' && DataTypecheckFirst != '%' && DataTypecheckFirst != '^' && DataTypecheckFirst != '&' && DataTypecheckFirst != '*' && DataTypecheckFirst != '(' && DataTypecheckFirst != ')') {
                        if (UserTextWord.get(Wordindex).contains("(") && UserTextWord.get(Wordindex).contains(")") && DataTypecheckLast == ')') {
                            for (int ConstantColor = DataTypeindex; ConstantColor < DataTypeindex + DataTypeValue.length(); ConstantColor++) {
                                UserTextCharacterColor[ConstantColor] = "CYAN";
                                //System.out.println("test1:     "+ConstantColor);
                            }
                        }
                        else {
                            for (int ConstantColor = DataTypeindex; ConstantColor < DataTypeindex + DataTypeValue.length(); ConstantColor++) {
                                UserTextCharacterColor[ConstantColor] = "MAGENTA";
                                //System.out.println("test1:     "+ConstantColor);
                            }
                            DataTypeindex = DataTypeindex + DataTypeValue.length();
                            DataTypeValueindex = DataTypeindex;
                            //System.out.println("test3:     " + DataTypeindex);
                            for (int SearchConstant = Wordindex + 1; SearchConstant < UserTextWord.size(); SearchConstant++) {
                                SearchDataValue = UserTextWord.get(SearchConstant);
                                if (SearchDataValue.equals(DataTypeValue)) {
                                    DataTypeValueindex = UserTextString.indexOf(SearchDataValue, DataTypeValueindex);
                                    for (int SeachDatatypeColor = DataTypeValueindex; SeachDatatypeColor < DataTypeValueindex + DataTypeValue.length(); SeachDatatypeColor++) {
                                        UserTextCharacterColor[SeachDatatypeColor] = "MAGENTA";
                                        //System.out.println("test2:     "+h);
                                    }
                                    DataTypeValueindex = DataTypeValueindex + SearchDataValue.length();
                                } else if (SearchDataValue.equals("(" + DataTypeValue) || SearchDataValue.equals("{" + DataTypeValue) || SearchDataValue.equals("[" + DataTypeValue)) {
                                    DataTypeValueindex = UserTextString.indexOf(SearchDataValue, DataTypeValueindex);
                                    for (int SeachDatatypeColor = DataTypeValueindex + 1; SeachDatatypeColor < DataTypeValueindex + DataTypeValue.length() + 1; SeachDatatypeColor++) {
                                        UserTextCharacterColor[SeachDatatypeColor] = "MAGENTA";
                                    }
                                    DataTypeValueindex = DataTypeValueindex + SearchDataValue.length();
                                } else if (SearchDataValue.equals(DataTypeValue + ")") || SearchDataValue.equals(DataTypeValue + "}") || SearchDataValue.equals(DataTypeValue + "]")) {
                                    DataTypeValueindex = UserTextString.indexOf(SearchDataValue, DataTypeValueindex);
                                    for (int SeachDatatypeColor = DataTypeValueindex; SeachDatatypeColor < DataTypeValueindex + DataTypeValue.length(); SeachDatatypeColor++) {
                                        UserTextCharacterColor[SeachDatatypeColor] = "MAGENTA";
                                    }
                                    DataTypeValueindex = DataTypeValueindex + SearchDataValue.length();
                                } else if (SearchDataValue.equals("(" + DataTypeValue + ")") || SearchDataValue.equals("{" + DataTypeValue + "}") || SearchDataValue.equals("[" + DataTypeValue + "]")) {
                                    DataTypeValueindex = UserTextString.indexOf(SearchDataValue, DataTypeValueindex);
                                    for (int SeachDatatypeColor = DataTypeValueindex + 1; SeachDatatypeColor < DataTypeValueindex + DataTypeValue.length() + 1; SeachDatatypeColor++) {
                                        UserTextCharacterColor[SeachDatatypeColor] = "MAGENTA";
                                    }
                                    DataTypeValueindex = DataTypeValueindex + SearchDataValue.length();
                                } else if (SearchDataValue.contains(DataTypeValue)) {
                                    DataTypeValueindex = UserTextString.indexOf(SearchDataValue, DataTypeValueindex);
                                    DataTypeValueindex = DataTypeValueindex + SearchDataValue.length();
                                }
                            }
                        }
                    }
                    else {
                        for (int j = DataTypeindex; j < UserTextCharacter.length; j++) {
                            if (UserTextCharacter[j] == '\n') {
                                break;
                            }
                            UserTextCharacterColor[j] = "ERROR";
                        }
                        DataTypeindex = DataTypeindex + DataTypeValue.length();
                        //System.out.println("test4:     " + DataTypeindex);
                    }
                }
            }
        }
    }
    public void MarkEdit() {
        Stack<Character> MarkEditStack = new Stack<Character>(); //Char형 스택 선언
        Stack<Integer> brackePosStack = new Stack<Integer>(); //Integer형 스택 선언
        int OpenRemarkPos = 0;
        int OpenQuotmarkPos = 0;
        int OpenBracketPos = 0;
        char BracketItem = ' ';
        for (int characterIndex = 0; characterIndex < UserTextCharacter.length; characterIndex++) {
            if (characterIndex != UserTextCharacter.length - 1) {
                if (MarkEditStack.size() != 0) {
                    if ((MarkEditStack.peek() != '"') && (MarkEditStack.peek() != '*') && (MarkEditStack.peek() != '+')) {
                        if ((characterIndex + 1 < UserTextCharacter.length) && ((UserTextCharacter[characterIndex] == '/') && (UserTextCharacter[characterIndex + 1] == '/'))) {
                            MarkEditStack.push('+');
                            for (int characterColor = characterIndex; characterColor < UserTextCharacter.length; characterColor++) {
                                if (UserTextCharacter[characterColor] == '\n') {
                                    break;
                                }
                                UserTextCharacterColor[characterColor] = "GREEN";
                            }
                        }
                        if ((UserTextCharacter[characterIndex] == '/') && (UserTextCharacter[characterIndex + 1] == '*')) {
                            MarkEditStack.push('*');
                            OpenRemarkPos = characterIndex;
                            for (int characterColor = characterIndex; characterColor < UserTextCharacter.length - 1; characterColor++) {
                                if (UserTextCharacter[characterColor] == '*' && UserTextCharacter[characterColor + 1] == '/') {
                                    break;
                                } else {
                                    UserTextCharacterColor[characterColor] = "ERROR";
                                    UserTextCharacterColor[characterColor + 1] = "ERROR";
                                }
                            }
                        }
                        if (((UserTextCharacter[characterIndex] == '*') && (UserTextCharacter[characterIndex + 1] == '/'))) {
                            UserTextCharacterColor[characterIndex] = UserTextCharacterColor[characterIndex + 1] = "ERROR";
                        }
                    }
                    if ((UserTextCharacter[characterIndex] == '\n') && (MarkEditStack.peek()) == '+') {
                        MarkEditStack.pop();
                        continue;
                    }
                    if ((MarkEditStack.peek() == '*')) {
                        if (UserTextCharacter[characterIndex] == '*' && UserTextCharacter[characterIndex + 1] == '/') {
                            MarkEditStack.pop();
                            for (int characterColor = OpenRemarkPos; characterColor < characterIndex + 2; characterColor++)
                                UserTextCharacterColor[characterColor] = "GREEN";
                            continue;
                        }
                    }
                } else {
                    if (((UserTextCharacter[characterIndex] == '/') && (UserTextCharacter[characterIndex + 1] == '/'))) {
                        MarkEditStack.push('+');
                        for (int characterColor = characterIndex; characterColor < UserTextCharacter.length; characterColor++) {
                            if (UserTextCharacter[characterColor] == '\n') {
                                break;
                            }
                            UserTextCharacterColor[characterColor] = "GREEN";
                        }
                    }
                    if (((UserTextCharacter[characterIndex] == '/') && (UserTextCharacter[characterIndex + 1] == '*'))) {
                        MarkEditStack.push('*');
                        OpenRemarkPos = characterIndex;
                        for (int characterColor = characterIndex; characterColor < UserTextCharacter.length - 1; characterColor++) {
                            if (UserTextCharacter[characterColor] == '*' && UserTextCharacter[characterColor + 1] == '/') {
                                break;
                            } else {
                                UserTextCharacterColor[characterColor] = "ERROR";
                                UserTextCharacterColor[characterColor + 1] = "ERROR";
                            }
                        }
                    }
                    if (UserTextCharacter[characterIndex] == '*' && UserTextCharacter[characterIndex + 1] == '/') {
                        UserTextCharacterColor[characterIndex] = UserTextCharacterColor[characterIndex + 1] = "ERROR";
                    }
                }
                if (UserTextCharacter[characterIndex] == '\'' && UserTextCharacterColor[characterIndex] != "ORANGE" && UserTextCharacterColor[characterIndex] != "GREEN") {
                    UserTextCharacterColor[characterIndex] = "ERROR";
                    if ((UserTextCharacter[characterIndex + 1] == '\'')) {
                        UserTextCharacterColor[characterIndex + 1] = UserTextCharacterColor[characterIndex] = "ORANGE";
                    } else if ((characterIndex != UserTextCharacter.length - 2) && UserTextCharacter[characterIndex + 2] == '\'') {
                        UserTextCharacterColor[characterIndex + 2] = UserTextCharacterColor[characterIndex + 1] = UserTextCharacterColor[characterIndex] = "ORANGE";
                    }
                }
            }
            if (MarkEditStack.size() != 0) {
                if (UserTextCharacter[characterIndex] == '"' && !(MarkEditStack.contains('"')) && (MarkEditStack.peek() != '+') && (MarkEditStack.peek() != '*')) {
                    MarkEditStack.push('"');
                    OpenQuotmarkPos = characterIndex;
                    UserTextCharacterColor[characterIndex] = "ERROR";
                    for (int characterColor = characterIndex + 1; characterColor < UserTextCharacter.length; characterColor++) {
                        if (UserTextCharacter[characterColor] == '"' || UserTextCharacter[characterColor] == '\n') {
                            break;
                        }
                        UserTextCharacterColor[characterColor] = "ERROR";
                    }
                }
                if (MarkEditStack.peek() == '"') {
                    //System.out.println(i);
                    //System.out.println(OpenQuotmarkPos);
                    if (UserTextCharacter[characterIndex] == '"' && characterIndex != OpenQuotmarkPos) {
                        MarkEditStack.pop();
                        for (int characterColor = OpenQuotmarkPos; characterColor < characterIndex + 1; characterColor++) {
                            UserTextCharacterColor[characterColor] = "ORANGE";
                            //System.out.println("test4");
                        }
                        continue;
                    } else if (UserTextCharacter[characterIndex] == '\n') {
                        //System.out.println("test4");
                        MarkEditStack.pop();
                        continue;
                    }
                }
            } else {
                if (UserTextCharacter[characterIndex] == '"') {
                    MarkEditStack.push('"');
                    OpenQuotmarkPos = characterIndex;
                    UserTextCharacterColor[characterIndex] = "ERROR";
                    for (int characterColor = characterIndex + 1; characterColor < UserTextCharacter.length; characterColor++) {
                        if (UserTextCharacter[characterColor] == '"' || UserTextCharacter[characterColor] == '\n') {
                            break;
                        }
                        UserTextCharacterColor[characterColor] = "ERROR";
                    }
                }
            }
            if (UserTextCharacter[characterIndex] == '{') {
                BracketItem = UserTextCharacter[characterIndex];
                OpenBracketPos = characterIndex;
                MarkEditStack.push(BracketItem);
                brackePosStack.push(OpenBracketPos);
                UserTextCharacterColor[characterIndex] = "ERROR";
            }
            if ((MarkEditStack.size() != 0) && MarkEditStack.contains('{')) {
                if ((MarkEditStack.peek() == '{') && (UserTextCharacter[characterIndex] == '}')) {
                    MarkEditStack.pop();
                    UserTextCharacterColor[brackePosStack.peek()] = UserTextCharacterColor[characterIndex] = "MAGENTA";
                    brackePosStack.pop();
                    continue;
                } else if (UserTextCharacter[characterIndex] == '}') {
                    UserTextCharacterColor[brackePosStack.peek()] = UserTextCharacterColor[characterIndex] = "ERROR";
                }
            } else if (UserTextCharacter[characterIndex] == '}') {
                UserTextCharacterColor[characterIndex] = "ERROR";
            }

            if (UserTextCharacter[characterIndex] == '[') {
                BracketItem = UserTextCharacter[characterIndex];
                OpenBracketPos = characterIndex;
                MarkEditStack.push(BracketItem);
                brackePosStack.push(OpenBracketPos);
                UserTextCharacterColor[characterIndex] = "ERROR";
            }
            if ((MarkEditStack.size() != 0) && MarkEditStack.contains('[')) {
                if ((MarkEditStack.peek() == '[') && (UserTextCharacter[characterIndex] == ']')) {
                    MarkEditStack.pop();
                    UserTextCharacterColor[brackePosStack.peek()] = UserTextCharacterColor[characterIndex] = "MAGENTA";
                    brackePosStack.pop();
                    continue;
                } else if (UserTextCharacter[characterIndex] == ']') {
                    UserTextCharacterColor[brackePosStack.peek()] = UserTextCharacterColor[characterIndex] = "ERROR";
                }
            } else if (UserTextCharacter[characterIndex] == ']') {
                UserTextCharacterColor[characterIndex] = "ERROR";
            }

            if (UserTextCharacter[characterIndex] == '(') {
                BracketItem = UserTextCharacter[characterIndex];
                OpenBracketPos = characterIndex;
                MarkEditStack.push(BracketItem);
                brackePosStack.push(OpenBracketPos);
                UserTextCharacterColor[characterIndex] = "ERROR";
            }
            if ((MarkEditStack.size() != 0) && MarkEditStack.contains('(')) {
                if ((MarkEditStack.peek() == '(') && (UserTextCharacter[characterIndex] == ')')) {
                    MarkEditStack.pop();
                    UserTextCharacterColor[brackePosStack.peek()] = UserTextCharacterColor[characterIndex] = "MAGENTA";
                    brackePosStack.pop();
                    continue;
                } else if (UserTextCharacter[characterIndex] == ')') {
                    UserTextCharacterColor[brackePosStack.peek()] = UserTextCharacterColor[characterIndex] = "ERROR";
                }
            } else if (UserTextCharacter[characterIndex] == ')') {
                UserTextCharacterColor[characterIndex] = "ERROR";
            }
        }
        int SemicolonIndex=0;
        int SemicolonCheck=0;
        for (int characterIndex =0 ; characterIndex < UserTextCharacter.length; characterIndex++) {
            SemicolonCheck=0;
            if (UserTextCharacter[characterIndex] == '\n') {
                //System.out.println("test1    "+SemicolonIndex);
                //System.out.println("test2    "+characterIndex);
                for (int characterColor = SemicolonIndex; characterColor < characterIndex; characterColor++) {
                    if(UserTextCharacter[characterColor] ==';'){
                        UserTextCharacterColor[characterColor] = "GRAY";
                        SemicolonCheck=1;
                    }
                    if(UserTextCharacter[characterColor] =='{' || UserTextCharacter[characterColor] =='}'){
                        SemicolonCheck=1;
                    }
                    if(UserTextCharacterColor[characterColor].equals("GREEN") || UserTextCharacterColor[characterColor].equals("PINK") || UserTextCharacterColor[characterColor].equals("CYAN")){
                        SemicolonCheck=1;
                    }
                }
                if(SemicolonCheck==0)
                {
                    for (int characterColor = SemicolonIndex; characterColor < characterIndex; characterColor++) {
                        UserTextCharacterColor[characterColor] = "ERROR";
                    }
                }
                SemicolonIndex=characterIndex+1;
            }
        }
    }
}



