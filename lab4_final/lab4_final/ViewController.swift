//
//  ViewController.swift
//  lab4_final
//
//  Created by Karan Motreja on 3/5/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    @IBOutlet weak var myTableView: UITableView!
    
    
    
    let url = URL(string: "https://api.nytimes.com/svc/movies/v2/reviews/search.json?api-key=EGAzwPax6NRzSUqFWioXitpXlwGIikXg")
    
    
    var myTableViewDataSource = [NewInfo]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        loadList()
        // Do any additional setup after loading the view, typically from a nib.
    }

    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 260
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return myTableViewDataSource.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell
    {
        
        let myCell = tableView.dequeueReusableCell(withIdentifier: "movieCell", for: indexPath)
        
        let myImageView = myCell.viewWithTag(11) as! UIImageView
        let myTitleLabel = myCell.viewWithTag(12) as! UILabel
        let myDateLabel = myCell.viewWithTag(13) as! UILabel
        
        myTitleLabel.text = myTableViewDataSource[indexPath.row].displayTitle
        myDateLabel.text = myTableViewDataSource[indexPath.row].openingDate
        
        let myURL = myTableViewDataSource[indexPath.row].src
        loadImage(url: myURL, to: myImageView)
        
        return myCell
    }
    
    
    
    func loadList()
    {
        var myNews = NewInfo()
        
        let task = URLSession.shared.dataTask(with: url!) {
            (data, response, error) in
            
            if error != nil
            {
                print("Houston, we have a problem.")
            }
            else
            {
                do
                {
                    if let content = data
                    {
                        let myJson = try JSONSerialization.jsonObject(with: content, options: .mutableContainers)
                    
                     //   print(myJson)
                        
                        if let jsonData = myJson as? [String : Any]
                        {
                            if let myResults = jsonData["results"] as? [[String : Any]]
                            {
                           //     dump(myResults)
                                
                                for value in myResults
                                {
                                    if let myTitle = value["display_title"] as? String
                                    {
                                        //print(myTitle)
                                        
                                        myNews.displayTitle = myTitle
                                    }
                                    
                                    if let myOpeningDate = value["opening_date"] as? String
                                    {
                                        //print(myTitle)
                                        
                                        myNews.openingDate = myOpeningDate
                                    }
                                    
                                    if let myMedia = value["multimedia"] as? [String : Any]
                                    {
                                        if let mySrc = myMedia["src"] as? String
                                        {
                                            myNews.src = mySrc
                                        }
                                    }
                                    
                                    self.myTableViewDataSource.append(myNews)
                                    
                                } // end of for loop
                                
                                dump(self.myTableViewDataSource)
                                
                                DispatchQueue.main.async
                                {
                                    self.myTableView.reloadData()
                                }
                                
                            }
                        }
                    }
                }
                
                catch
                {
                    
                }
            }
        }
         task.resume()
    } // end of function
    
    func loadImage(url: String, to imageView: UIImageView)
    {
        
        let url = URL(string: url)
        URLSession.shared.dataTask(with: url!) { (data, response, error) in
            
            guard let data = data else
            {
                return
            }
            DispatchQueue.main.async {
                imageView.image = UIImage(data: data)
            }
            
        }.resume()
    }
    
    
    
} // end of class

