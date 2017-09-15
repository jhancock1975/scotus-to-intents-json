# scotus-to-intents-json
convert supreme court transcript to intent.json format as defined in: https://chatbotsmagazine.com/contextual-chat-bots-with-tensorflow-4391749d0077

This repository is for code that converts transcripts of 
U.S. Supreme Court proceedings to a JSON format that is defined
in the tutorial on using tensorflow for chatbots mentioned above.

Donload transcripts using the command: `wget https://github.com/pender/chatbot-rnn/raw/master/data/scotus/scotus.bz2 && bzip2 -dk scotus.bz2 && rm scotus.bz2`

Note: we found the download command here: https://github.com/Conchylicultor/DeepQA/tree/master/data/scotus 
September 15th, 2017
