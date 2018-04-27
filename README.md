# Implementation and Demo of:
# A probabilistic model to expand abbreviations in source code.

# Overview:
This document provides details and instructions on how to access and run our algorithm of both approaches (unigram and bigram). We present two methods to test our algorithm: (1) a web-application, and (2) a python tool. The web-application can be used only to test expanding one abbreviation, however, the python tool can be used to expand any number of abbreviations. In the following, we present details, usage instructions for each method.

# Web-Application:
We implemented our algorithm using python and deployed it on a hosting server called “pythonanywhere.com”. The application can only process one abbreviation and one source code at a time. The application consists of two fields: (1) The abbreviation as a string of alphabetic characters, and (2) The source code that contains the abbreviation. The application automatically determines the line number of the abbreviation 

The application URL is: http://seuresearch.pythonanywhere.com/

*Sample code for the application:*
You may use start testing our web-application using the same running example we presented in our research.
1) Abbreviation: afl
2) Source code:
//Print list of affiliated faculty members
// forms from applications forms list
void pAF(ArraryList<Member> afl){
      for(Member mem : afl){
          println(mem)
      }
}

# Python tool:
We implemented our algorithm using python in a form of source code named "code_v_4_24_2018_v2.py". It is runnable using python after installing the required libraries. The python tool can be python tool can be used to expand any number of abbreviations. In the following, we present instructions on how to setup and run the python tool.

*Instructions*
1) Install the latest version of Python, or at least Python v4.11.18
2) Download the zipped folder "code_v_4_24_2018_v2.zip"
3) Unzip the folder, and run the python code after installing the required libraries, which are:
                    - pip3 install wordsegment
                    - pip3 install python-docx
4) In the parameters area at the end of the code, change to the desired input folder, but make sure:
                    - The abbreviation sets are stored in a CSV file called abbs.csv
                        and each set consist of [Abbreviation, Line number, Manulually found expansion]
                    - The source code files and they should be named so it follows the order of their associated 
                      abbreviations in "abbs.csv"
5) The tool will present computation details in case you are testing one abbreviation, but if you are testing more than one abbreviation, the code will give a summary of each abbreviation expansion, and then calculate the expansion accuracy. 
6) You may test the python tool using the 100_methods provided in the zipped folder, or test the running example afl_bigram.



# Research Abstract

Expanding abbreviations in source code to their full meanings is very useful for software maintainers to comprehend the source code. The existing approaches, however, focus on expanding an abbreviation to a single word, i.e., unigram. They do not perform well when dealing with abbreviations of phrases that consist of multiple unigrams. This paper proposes a bigram-based approach for retrieving abbreviated phrases automatically. Key to this approach is a bigram-based inference model for choosing the best phrase from all candidates. It utilizes the statistical properties of unigrams and bigrams as prior knowledge and a bigram language model for estimating the likelihood of each candidate phrase of a given abbreviation. We have applied the bigram-based approach to 100 phrase abbreviations, randomly selected from eight open source projects. The experiment results show that it has correctly retrieved 78% of the abbreviations by using the unigram and bigram properties of a source code repository. This is 9% more accurate than the unigram-based approach and much better than other existing approaches. The bigram-based approach is also less biased towards specific phrase sizes than the unigram-based approach. 
