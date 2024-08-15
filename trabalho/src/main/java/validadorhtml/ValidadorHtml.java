package validadorhtml;

import java.io.File;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;
import validadorhtml.pilha.PilhaLista;
import validadorhtml.pilha.PilhaVaziaException;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorHtml {
    
    public DefaultTableModel tableModel;
    public boolean isWellFormatted; 
    public PilhaLista<String> tagStack;
    public String tagWithouClosing;
    public String feedbackWrongFinalTag;
    
    public ValidadorHtml(){
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Tag");
        tableModel.addColumn("Número de ocorrências");
        isWellFormatted = true;
        tagStack = new PilhaLista<>();
        tagWithouClosing = "";
        feedbackWrongFinalTag = "";
    }
    
    public String scanFile(File htmlPage){
        try{
            Scanner scanner = new Scanner(htmlPage, "UTF-8");
            
            while(scanner.hasNextLine()){ 
                String pageLine = scanner.nextLine();
                
                Pattern htmlTagPattern = Pattern.compile("<([^>]+)>");
                Matcher htmlTagMatcher = htmlTagPattern.matcher(pageLine);
                
                findAndCleanTags(htmlTagMatcher); 
            }
           
            scanner.close();
            
            return giveFeedback();
        } catch(FileNotFoundException exception){
            return "O arquivo não foi encontrado.";
        }
    }
       
    public void findAndCleanTags(Matcher htmlTagMatcher){
        while (htmlTagMatcher.find()) {
            String cleanTag = htmlTagMatcher.group(0).replaceAll("\\s+\\w+(=\"[^\"]*\")?", "");
            PilhaLista<String> cleanTagLetters = new PilhaLista<String>();
            boolean isFinalTag = false;
            
            for (int letter = cleanTag.length()-2; letter > 0; letter--) {
                if(cleanTag.charAt(letter) == '/' && letter == 1){
                    isFinalTag = true;
                } else if(cleanTag.charAt(letter) != '/' && cleanTag.charAt(letter) != ' '){
                    cleanTagLetters.push(String.valueOf(cleanTag.charAt(letter)).toLowerCase());
                }   
            }
            
            String tagWithoutSignals = "";
            
            while(!cleanTagLetters.estaVazia()){
                tagWithoutSignals += cleanTagLetters.pop();
            }
            
            redirectTagByType(tagWithoutSignals, isFinalTag);
        }
    }
    
    public void redirectTagByType(String tagWithoutSignals, boolean isFinalTag){
        if(tagWithoutSignals.equals("meta") || tagWithoutSignals.equals("base") || tagWithoutSignals.equals("br") || tagWithoutSignals.equals("col") ||
            tagWithoutSignals.equals("command") || tagWithoutSignals.equals("embed") || tagWithoutSignals.equals("hr") || tagWithoutSignals.equals("img") ||
            tagWithoutSignals.equals("input") || tagWithoutSignals.equals("link") || tagWithoutSignals.equals("param") || tagWithoutSignals.equals("source") ||
            tagWithoutSignals.equals("!doctype") && !addTagIfAlreadyExists(tagWithoutSignals)){
            Object[] tableLine = {tagWithoutSignals, "1"};
            tableModel.addRow(tableLine);                    
        } else if (isFinalTag){
            try{
                String openingTag = tagStack.pop();
                compareTags(openingTag, tagWithoutSignals);
            } catch(PilhaVaziaException exception){}
        } else {
            tagStack.push(tagWithoutSignals);
        }
    }
    
    public void compareTags(String openingTag, String closingTag){
        if(!openingTag.equals(closingTag)){
            if(feedbackWrongFinalTag.equals("")){
                feedbackWrongFinalTag =  "Aguardava-se a tag final </" + openingTag + "> mas foi encontrada a tag </" + closingTag + ">";
                tagWithouClosing = openingTag;
            }
            isWellFormatted = false;
        } else if(!addTagIfAlreadyExists(openingTag)){
            Object[] tableLine = {openingTag, "1"};
            tableModel.addRow(tableLine);
        }
    }
    
    public boolean addTagIfAlreadyExists(String tag){
        boolean foundTag = false;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String firstCellValue = (String) tableModel.getValueAt(i, 0);

            if (firstCellValue.equals(tag)) {
                int qtdeOcorrencias = Integer.parseInt((String) tableModel.getValueAt(i, 1)) + 1;
                tableModel.setValueAt(String.valueOf(qtdeOcorrencias), i, 1);
                foundTag = true;
            }
        }

        return foundTag;
    }

    public String giveFeedback(){    
        if(isWellFormatted){  
            return "O arquivo está bem formatado.";
        } else if(tagStack.estaVazia()){
            return feedbackWrongFinalTag;
        } else {
           return buildTagsWithoutFinalsTags();
        }
    }
    
    public String buildTagsWithoutFinalsTags(){
        String penultimateTag = "";
        String lastTag = tagStack.pop();
        StringBuilder feedbackTagsWithoutClosing = new StringBuilder("Aguardava-se as seguintes tags finais:\n");

        while (!tagStack.estaVazia()) {
            penultimateTag = lastTag;
            feedbackTagsWithoutClosing.append("</").append(penultimateTag).append(">\n");
            lastTag = tagStack.pop();
        }
        
        feedbackTagsWithoutClosing.append("</").append(tagWithouClosing).append(">\n").append("mas não foi encontrada nenhuma.");
        
        return feedbackTagsWithoutClosing.toString();
    }
    
}
