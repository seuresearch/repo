# Expand: An implementation of a probabilistic model to expand abbreviations in source code. 

# Overview:
This document provides details and instructions on how to access and run our python tool "Expand". We present two methods to test our algorithm: (1) a web-application, and (2) a python tool. The web-application can be used only to test expanding one abbreviation, however, the python tool can be used to expand any number of abbreviations. In the following, we present details, usage instructions for each method.

# The Web-Application "Expand":
We implemented our algorithm using python and deployed it on a hosting server called “pythonanywhere.com”. The application can only expand one given abbreviation at each run. The application can be accessed using this URL: http://seuresearch.pythonanywhere.com/

*Sample code for testing:*
You may test our web-application using the following example.
1) Abbreviation: ``` afl ```
2) Source code:
```
//Print list of affiliated faculty members
// forms from applications forms list
void pAFL(ArraryList<Member> afl){
      for(Member mem : afl){
          println(mem)
      }
}
```

# The Python tool "Expand":
We implemented our algorithm as pythion tool using python programming language, and it is available in script file "code_v_4_24_2018_v2.py". It is runnable using python after installing the required libraries. The python tool can be used to expand any number of abbreviations. The following instructions explain how to setup and run the python tool "Expand".

*Instructions*
1) Install the latest version of Python, or at least Python v4.11.18
2) Download the zipped folder "code_v_4_24_2018_v2.zip"
3) Unzip the folder, and run the python code after installing the required libraries, which are:
                    - pip3 install wordsegment
                    - pip3 install python-docx
4) In the parameters area at the end of the script file, change the parameters to the desired input folder, and make sure:
                    - The abbreviation sets are stored in a CSV file called abbs.csv
                        and each set is structured as follow: [Abbreviation, Line number, Manulually found expansion]
                    - The source code files are named following the order of their associated 
                      abbreviations in "abbs.csv"
5) The tool will present computation details in case you are testing one abbreviation, but if you are testing more than one, the code will give a summary of each abbreviation expansion, and then calculate the expansion accuracy. 
6) You may test the python tool using the 100_methods provided in the zipped folder, or expand the abbreviation "afl" in the folder "afl_bigram".



# Research Abstract

Expanding abbreviations in source code to their full meanings is very useful for software maintainers to comprehend the source code. The existing approaches, however, focus on expanding an abbreviation to a single word, i.e., unigram. They do not perform well when dealing with abbreviations of phrases that consist of multiple unigrams. In this GitHub repository, we provide a pythin tool called "Expand" for retrieving the original meaning of a given abbreviation. It automatically extracts a list of potential candidates from the given source code and uses a probabilistic model based on Bayesian inference to estimate the likelihood of a candidate being the correct expansion. The probabilistic model relies on the unigram and bigram language models and utilizes statistical abbreviations’ patterns as evidence to estimate the likelihood of a candidate being the correct expansion.
