# A Bigram-based Inference Model for Retrieving Abbreviated Phrases in Source Code

# abstract

Expanding abbreviations in source code to their full meanings is very useful for software maintainers to comprehend the source code. The existing approaches, however, focus on expanding an abbreviation to a single word, i.e., unigram. They do not perform well when dealing with abbreviations of phrases that consist of multiple unigrams. This paper proposes a bigram-based approach for retrieving abbreviated phrases automatically. Key to this approach is a bigram-based inference model for choosing the best phrase from all candidates. It utilizes the statistical properties of unigrams and bigrams as prior knowledge and a bigram language model for estimating the likelihood of each candidate phrase of a given abbreviation. We have applied the bigram-based approach to 100 phrase abbreviations, randomly selected from eight open source projects. The experiment results show that it has correctly retrieved 81\% of the abbreviations by using the unigram and bigram properties of a source code repository. This is 11\% more accurate than the unigram-based approach and much better than other existing approaches. The bigram-based approach is also less biased towards specific phrase sizes than the unigram-based approach. 
