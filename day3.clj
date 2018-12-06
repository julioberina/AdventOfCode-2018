(let [inputs (-> (slurp "day3input.txt")
                 (clojure.string/split #"\n")
                 (->> (map #(re-seq #"\d+" %)))
                 (->> (map rest)))
      grid (to-array-2d (repeat 1001 (repeat 1001 0)))
      third (fn [lst] (nth lst 2))
      fourth (fn [lst] (nth lst 3))
      overlap (atom 0)
      claim (atom 1)
      no-overlaps (atom true)]

  (doseq [inplist inputs]
    (let [input (map #(Integer/parseInt %) inplist)]
      (doseq [i (range (second input) (+ (second input) (fourth input)))]
        (let [array (aget grid i)]
          (doseq [j (range (first input) (+ (first input) (third input)))]
            (aset array j (inc (aget array j))))))))

  (doseq [row grid]
    (doseq [col row]
      (if (> col 1) (swap! overlap inc))))

  (println @overlap "overlaps")

  (doseq [inplist inputs]
    (let [input (map #(Integer/parseInt %) inplist)]
      (doseq [i (range (second input) (+ (second input) (fourth input)))]
        (let [array (aget grid i)]
          (doseq [j (range (first input) (+ (first input) (third input)))]
            (if (not= (aget array j) 1) (reset! no-overlaps false)))))

      (if (true? @no-overlaps) (println "Claim" @claim "has no overlaps"))
      (reset! no-overlaps true)
      (swap! claim inc))))
