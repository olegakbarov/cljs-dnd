
(ns cljs-dnd.state)

(def state (atom {:dix nil       ;; drag-index
                  :hix nil       ;; hover-index
                  :cli-y nil}))  ;; clientY
