package com.cardify.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FlashcardGenerationType {

    BOOK("""
            Analyze the attached book file. Select %s unique conversational words from these file and following these criteria:
            
            Exclusions:
            No need to invent words, words should be taken only from the file
            Do not include abbreviations, character or actor names, place names, or brand names.
            Exclude articles (e.g., a, the), pronouns (e.g., I, you, he, she), conjunctions (e.g., and, but), prepositions (e.g., in, on, with), and particles (e.g., just, only).
            Inclusions:
            Focus on words that are commonly used in everyday conversations.
            
            Uniqueness:
            Ensure all selected words are non-repetitive.
            Response Structure:
            
            Return a JSON object with a field "keywords", which is an array of conversational words. Do not return a stringified JSON. Just return the object.
            """,
            """
                    Analyze the attached book file. The language of the book and words is the target language I am learning.
                    My native language is %s.
                    Word List: %s;
                    Ensure that the number of flashcards exactly matches the number of words in the list — one flashcard per word.
                    Avoid duplicates: each word should appear only once in the flashcard list.
                    
                    File Parsing Details:
                    Treat the book file as the primary source for the contexts field.
                    If necessary, clean up formatting artifacts (e.g., speaker labels) to ensure smooth sentence construction.
                    
                    Word-to-Context Matching:
                    Match each word from the list to its relevant passage in the book file and select only those text context, which contains this word.
                    Ensure the example demonstrates how the word functions within that specific context (e.g., as a verb, noun, or adjective).
                    
                    Response Requirements:
                    Context from the Book:
                    If the context is too long, shorten it, but make sure that it contains the word you are looking for and the meaning of the context.
                    Extract an example sentence from the book where each word from the list occurs.
                    
                    Data Cleaning:
                    Remove any extraneous formatting, such as chapter headings, page numbers, or annotations.
                    Retain only clean, readable text from the book to construct example sentences.
                    
                    Response Structure:
                    For each word, include:
                    Translations of words and translations of contexts must be in language of the book
                    """),
    YOUTUBE("""
            Analyze the attached subtitle file from youtube video. Select %s unique conversational words from these file and following these criteria:
            
            Exclusions:
            No need to invent words, words should be taken only from the file
            Do not include abbreviations, character or actor names, place names, or brand names.
            Exclude articles (e.g., a, the), pronouns (e.g., I, you, he, she), conjunctions (e.g., and, but), prepositions (e.g., in, on, with), and particles (e.g., just, only).
            Inclusions:
            Focus on words that are commonly used in everyday conversations.
            
            Uniqueness:
            Ensure all selected words are non-repetitive.
            """,
            """
                    Analyze the attached subtitle file. The language of the subtitles and words is the target language I am learning.
                    My native language is %s.
                    Word List: %s;
                    Ensure that the number of flashcards exactly matches the number of words in the list — one flashcard per word.
                    Avoid duplicates: each word should appear only once in the flashcard list.
                    
                    File Parsing Details:
                    Treat the subtitle file as the primary source for the contexts field.
                    If necessary, clean up formatting artifacts (e.g., speaker labels, timestamps, or repetitive filler words) to ensure smooth sentence construction.
                    
                    Word-to-Context Matching:
                    Match each word from the list to its relevant passage in the subtitles file and select only those text context, which contains this word.
                    Ensure the example demonstrates how the word functions within that specific context (e.g., as a verb, noun, or adjective).
                    
                    Response Requirements:
                    Context from the Subtitles:
                    If the context is too long, shorten it, but make sure that it contains the word you are looking for and the meaning of the context.
                    Extract an example sentence from the subtitles where each word from the list occurs.
                    
                    Data Cleaning:
                    Remove any extraneous formatting, such as timestamps, speaker labels, or background annotations (e.g., [Applause], [Music], etc.).
                    Retain only clean, readable text from the subtitles to construct example sentences.
                    
                    Response Structure:
                    For each word, include:
                    Translations of words and translations of contexts must be in language of the transcript
                    """),
    IMAGE("""
            Analyze the provided OCR-extracted text from the attached file. Select %s unique conversational words from this text, following these criteria:
            
            Exclusions:
            No need to invent words, words should be taken only from the provided text.
            Do not include abbreviations, character or actor names, place names, or brand names.
            Exclude articles (e.g., a, the), pronouns (e.g., I, you, he, she), conjunctions (e.g., and, but), prepositions (e.g., in, on, with), and particles (e.g., just, only).
            
            Inclusions:
            Focus on words that are commonly used in everyday conversations.
            
            Uniqueness:
            Ensure all selected words are non-repetitive.
            
            Response Structure:
            
            Return a JSON object with a field "keywords", which is an array of conversational words. Do not return a stringified JSON. Just return the object.
            """,
            """
                    Analyze the provided OCR-extracted text from the attached file. The language of the text and words is the target language I am learning.
                    My native language is %s.
                    Word List: %s;
                    Ensure that the number of flashcards exactly matches the number of words in the list — one flashcard per word.
                    Avoid duplicates: each word should appear only once in the flashcard list.
                    
                    File Parsing Details:
                    Treat the OCR-extracted text as the primary source for the contexts field.
                    If necessary, clean up formatting artifacts (e.g., line breaks, headers, visual layout symbols) to ensure smooth sentence construction.
                    
                    Word-to-Context Matching:
                    Match each word from the list to its relevant text fragment and select only those context examples that include the word.
                    Ensure the example demonstrates how the word functions within that specific context (e.g., as a verb, noun, or adjective).
                    
                    Response Requirements:
                    Context from the Text:
                    If the context is too long, shorten it, but make sure that it contains the word you are looking for and the meaning of the context.
                    Extract an example sentence from the text where each word from the list occurs.
                    
                    Data Cleaning:
                    Remove any extraneous formatting or layout artifacts.
                    Retain only clean, readable text to construct example sentences.
                    
                    Response Structure:
                    For each word, include:
                    Translations of words and translations of contexts must be in language of the input OCR-extracted text.
                    """);

    private final String wordsPrompt;
    private final String cardsPrompt;

    public String formatWordsPrompt(Object... args) {
        return this.wordsPrompt.formatted(args);
    }

    public String formatCardsPrompt(Object... args) {
        return this.cardsPrompt.formatted(args);
    }
}
