
(ns cljs-dnd.wrapper)


(defn drag-source [type comp target conn-fn]
  "HOC that registers components as source of drag-start event
  @type is enum
  @comp is component to decorate
  @conn-fn connects to drop target")

(defn drop-target [type comp source conn-fn]
  "HOC that registers components as target to on-drop event
  @type is enum
  @comp is component to decorate
  @conn-fn accepts @connect & @monitor")

; (defn draggable []
;   (reagent/create-class {:reagent-render draggable-render
;                          :component-did-mount draggable-did-mount}))
