import React, { useState } from 'react';

// Define type for each title
interface TableColumn {
  title: string;
  type: string;
}

interface TableProps {
  titles: TableColumn[];
  data: { [key: string]: any }[];
  onEdit: (rowIndex: number, editedData: { [key: string]: any }) => void;
}

export const TableComponent: React.FC<TableProps> = ({ titles, data, onEdit }) => {
  const [editableRow, setEditableRow] = useState<number | null>(null);
  const [editedData, setEditedData] = useState<{ [key: string]: any }>({});
  console.log(data);
  

  const startEdit = (rowIndex: number) => {
    setEditableRow(rowIndex);
    setEditedData(data[rowIndex]); // Initialize editedData with the row's data
  };

  const handleEdit = (colIndex: number, value: any) => {
    // Update editedData with the new value
    const columnTitle = titles[colIndex].title;
    setEditedData((prevData) => ({ ...prevData, [columnTitle]: value }));
  };

  const saveEdit = (rowIndex: number) => {
    onEdit(rowIndex, editedData); // Send the merged data
    setEditableRow(null);
    setEditedData({});
  };

  const cancelEdit = () => {
    setEditableRow(null);
    setEditedData({});
  };

  return (
    <div className="overflow-x-auto">
      <table className="min-w-full divide-y divide-gray-200">
        <thead className="bg-gray-50">
          <tr>
            {titles.map((column, index) => (
              <th
                key={index}
                scope="col"
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
              >
                {column.title}
              </th>
            ))}
            <th scope="col" className="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
              Actions
            </th>
          </tr>
        </thead>
        <tbody className="bg-white divide-y divide-gray-200">
          {data.map((row, rowIndex) => (
            <tr key={rowIndex}>
              {titles.map((column, colIndex) => {
                let cellData = row[column.title];
                if (Array.isArray(cellData)) {
                  cellData = cellData.join(", ");
                }
                return (
                  <td key={colIndex} className="px-6 py-4 whitespace-nowrap">
                    {editableRow === rowIndex ? (
                      <input
                        type={column.type}
                        value={editedData[column.title] || ''}
                        onChange={(e) => handleEdit(colIndex, e.target.value)}
                        className="border-gray-300 focus:ring-blue-500 focus:border-blue-500 block w-full rounded-md sm:text-sm"
                      />
                    ) : (
                      cellData
                    )}
                  </td>
                );
              })}
              <td className="px-6 py-4 whitespace-nowrap text-center text-sm font-medium">
                {editableRow === rowIndex ? (
                  <div>
                    <button
                      onClick={() => saveEdit(rowIndex)}
                      className="text-indigo-600 hover:text-indigo-900 mr-2">
                      Save
                    </button>
                    <button
                      onClick={cancelEdit}
                      className="text-gray-600 hover:text-gray-900">
                      Cancel
                    </button>
                  </div>
                ) : (
                  <button
                    onClick={() => startEdit(rowIndex)}
                    className="text-indigo-600 hover:text-indigo-900">
                    Edit
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
