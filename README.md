# anansi

> An "actually-read-later" app in ClojureScript/React

I save many times more links than I get around to reading. This was a whack at a full-stack React application in Clojure & ClojureScript to interact with links that I've saved to Pinboard. It might have been an attempt to solve too many problems at once, though.

Features desired:
- train a word2vec model on tweets written, tweets pinned, and articles saved
- given a bit of free text, return a word vector
- present the reader with links to review based on recency
- present the reader with links to review based on likely topic (via 1. explicit tagging and 2. word vector)
- present "cards" representing links that the reader can arrange so that cards that seem "near" one another become more strongly associated than the automated bits of the system realized
