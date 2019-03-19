//
//  GroceryTableViewController.swift
//  lab5
//
//  Created by Karan Motreja on 3/19/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import UIKit

class GroceryTableViewController: UITableViewController {

    
    @IBAction func addGroceryItem(_ sender: UIBarButtonItem)
    {
        let addalert = UIAlertController(title: "New Item", message: "Add a new item to your grocery list", preferredStyle: .alert)
        addalert.addTextField(configurationHandler: {(UITextField) in
        })
        let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: nil)
        addalert.addAction(cancelAction)
        let addItemAction = UIAlertAction(title: "Add", style: .default, handler: {(UIAlertAction)in
            let newitem = addalert.textFields![0]
            let newGroceryItem = Grocery()
            newGroceryItem.name = newitem.text!
            newGroceryItem.bought = false
            self.groceryData.addItem(newItem: newGroceryItem)
            self.render()
        })
        addalert.addAction(addItemAction)
        present(addalert, animated: true, completion: nil)

    }
    
    
    
    var groceryData = GroceryDataController()
    
    var groceryList = [Grocery]()
    
    func render(){
        groceryList=groceryData.getGroceries()
        
        tableView.reloadData()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        groceryData.dbSetup()
        groceryList=groceryData.getGroceries()
       
    }

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return groceryList.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        let item = groceryList[indexPath.row]
        cell.textLabel!.text = item.name
        cell.accessoryType = item.bought ? .checkmark : .none 
        return cell
    }
    
    
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
       
        return true
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let boughtItem = groceryList[indexPath.row]
        groceryData.boughtItem(item: boughtItem)
        render()
    }
    
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            let item = groceryList[indexPath.row]
            groceryData.deleteItem(item: item)
            render()
        } else if editingStyle == .insert {
            
        }
    }

}
