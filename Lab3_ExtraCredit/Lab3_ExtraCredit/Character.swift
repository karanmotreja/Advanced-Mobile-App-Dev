//
//  Character.swift
//  Lab3_ExtraCredit
//
//  Created by Karan Motreja on 3/20/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import Foundation

struct Character: Decodable{
    let name : String
    let url : String
}

class CharacterDataModelController{
    var allData = [Character]()
    let fileName = "harrypotter2"
    
    func loadData(){
        if let pathURL = Bundle.main.url(forResource: fileName, withExtension: "plist"){
            //creates a property list decoder object
            let plistdecoder = PropertyListDecoder()
            do {
                let data = try Data(contentsOf: pathURL)
                //decodes the property list
                allData = try plistdecoder.decode([Character].self, from: data)
            } catch {
                // handle error
                print(error)
            }
        }
    }
    
    func getCharacters() -> [String]{
        var characters = [String]()
        for character in allData{
            characters.append(character.name)
        }
        return characters
    }
    
    func getURL(index:Int) -> String {
        return allData[index].url
    }
}
