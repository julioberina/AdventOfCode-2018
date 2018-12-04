(defn gather [input]
  (let [pos (clojure.string/split (first (re-seq #"\d+,\d+" input)) #",")
        dim (clojure.string/split (first (re-seq #"\d+x\d+" input)) #"x")]
    (concat (map #(Integer/parseInt %) pos) (map #(Integer/parseInt %) dim))))

(defn third [lst] (nth lst 2))
(defn fourth [lst] (nth lst 3))

(let [inputs (map gather (clojure.string/split (slurp "day3input.txt") #"\n"))
      seen (atom #{})
      square_inches (atom 0)]

  (doseq [line inputs]
    (doseq [i (range (second line) (inc (+ (second line) (fourth line))))]
      (doseq [j (range (first line) (inc (+ (first line) (third line))))]
        (if (contains? @seen [j i])
          (swap! square_inches inc)
          (swap! seen conj [j i])))))

  (println "Square inches:" @square_inches))
