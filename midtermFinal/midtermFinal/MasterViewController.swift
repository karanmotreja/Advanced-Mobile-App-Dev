//
//  MasterViewController.swift
//  midtermFinal
//
//  Created by Karan Motreja on 3/14/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import UIKit

class MasterViewController: UITableViewController, UISearchBarDelegate {

    @IBOutlet weak var searchBar: UISearchBar!
    
    var detailViewController: DetailViewController? = nil
    var movies = [String]()
    var movieData = MovieDataModelController()
    
    var isSearching = false
    
    var filteredData = [String]()
   
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        movieData.loadData()
        movies = movieData.getMovies()
        
        searchBar.returnKeyType = UIReturnKeyType.done
    }

    override func viewWillAppear(_ animated: Bool) {
        clearsSelectionOnViewWillAppear = splitViewController!.isCollapsed
        super.viewWillAppear(animated)
    }

   
    // MARK: - Segues

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showDetail" {
            if let indexPath = self.tableView.indexPathForSelectedRow {
                let url = movieData.getURL(index: indexPath.row)
                let name = movies[indexPath.row]
                let controller = (segue.destination as! UINavigationController).topViewController as! DetailViewController
                controller.detailItem = url
                controller.title = name
                controller.navigationItem.leftBarButtonItem = self.splitViewController?.displayModeButtonItem
                controller.navigationItem.leftItemsSupplementBackButton = true
            }
        }
    }

    // MARK: - Table View

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        if isSearching
        {
            return filteredData.count
        }
        
        return movies.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath)
        cell.textLabel!.text = movies[indexPath.row]
        
        let text: String!
        
        if isSearching
        {
            text = filteredData[indexPath.row]
        }
        else
        {
            text = movies[indexPath.row]
        }
        
        return cell
    }
    
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == UITableViewCell.EditingStyle.delete
        {
            movies.remove(at: indexPath.row)
            tableView.reloadData()
        }
    }

    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }

    
    
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        if searchBar.text == nil || searchBar.text == ""
        {
            isSearching = false
            view.endEditing(true)
            
            tableView.reloadData()
        }
        else
        {
            isSearching = true
            filteredData = movies.filter({$0.contains(searchBar.text!)})
            
            tableView.reloadData()
        }
    }

}

