import React, { useState, ChangeEvent } from 'react';

interface TableColumn {
  title: string;
  type: string;
}

interface TableProps {
  titles: TableColumn[];
  data: { [key: string]: any }[];
  onEdit: (rowIndex: number, editedData: { [key: string]: any }) => void;
  onCreate: (newData: { [key: string]: any }) => void;
  onFileChange: (file: File, newData: { [key: string]: any }, callback: (fileUrl: string) => void) => void; // New prop for file upload
}

export const TableComponent: React.FC<TableProps> = ({ titles, data, onEdit, onCreate, onFileChange }) => {
  const [editableRow, setEditableRow] = useState<number | null>(null);
  const [editedData, setEditedData] = useState<{ [key: string]: any }>({});
  const [isCreating, setIsCreating] = useState<boolean>(false);
  const [newRecordData, setNewRecordData] = useState<{ [key: string]: any }>({});

  const startEdit = (rowIndex: number) => {
    setEditableRow(rowIndex);
    setEditedData(data[rowIndex]);
  };

  const handleEdit = (colIndex: number, value: any) => {
    const columnTitle = titles[colIndex].title;
    setEditedData((prevData) => ({ ...prevData, [columnTitle]: value }));
  };

  const handleFileEdit = (colIndex: number, file: File) => {
    onFileChange(file, editedData, (fileUrl) => {
      const columnTitle = titles[colIndex].title;
      setEditedData((prevData) => ({ ...prevData, [columnTitle]: fileUrl }));
    });
  };

  const saveEdit = (rowIndex: number) => {
    onEdit(rowIndex, editedData);
    setEditableRow(null);
    setEditedData({});
  };

  const cancelEdit = () => {
    setEditableRow(null);
    setEditedData({});
  };

  const startCreate = () => {
    setIsCreating(true);
    setNewRecordData({});
  };

  const handleCreate = (colIndex: number, value: any) => {
    const columnTitle = titles[colIndex].title;
    setNewRecordData((prevData) => ({ ...prevData, [columnTitle]: value }));
  };

  const handleFileCreate = (colIndex: number, file: File) => {
    onFileChange(file, newRecordData, (fileUrl) => {
      const columnTitle = titles[colIndex].title;
      setNewRecordData((prevData) => ({ ...prevData, [columnTitle]: fileUrl }));
    });
  };

  const saveCreate = () => {
    onCreate(newRecordData);
    setIsCreating(false);
    setNewRecordData({});
  };

  const cancelCreate = () => {
    setIsCreating(false);
    setNewRecordData({});
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
                  cellData = cellData.join(', ');
                }
                return (
                  <td key={colIndex} className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {editableRow === rowIndex ? (
                      column.type === 'file' ? (
                        <input
                          type="file"
                          onChange={(e: ChangeEvent<HTMLInputElement>) =>
                            handleFileEdit(colIndex, e.target.files![0])
                          }
                        />
                      ) : (
                        <input
                          type={column.type}
                          value={editedData[column.title] || ''}
                          onChange={(e) => handleEdit(colIndex, e.target.value)}
                        />
                      )
                    ) : (
                      cellData
                    )}
                  </td>
                );
              })}
              <td className="px-6 py-4 whitespace-nowrap text-center text-sm font-medium">
                {editableRow === rowIndex ? (
                  <>
                    <button onClick={() => saveEdit(rowIndex)} className="text-indigo-600 hover:text-indigo-900">
                      Save
                    </button>
                    <button onClick={cancelEdit} className="text-red-600 hover:text-red-900 ml-4">
                      Cancel
                    </button>
                  </>
                ) : (
                  <>
                    <button onClick={() => startEdit(rowIndex)} className="text-indigo-600 hover:text-indigo-900">
                      Edit
                    </button>
                  </>
                )}
              </td>
            </tr>
          ))}
          {isCreating && (
            <tr>
              {titles.map((column, colIndex) => (
                <td key={colIndex} className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                  {column.type === 'file' ? (
                    <input
                      type="file"
                      onChange={(e: ChangeEvent<HTMLInputElement>) =>
                        handleFileCreate(colIndex, e.target.files![0])
                      }
                    />
                  ) : (
                    <input
                      type={column.type}
                      value={newRecordData[column.title] || ''}
                      onChange={(e) => handleCreate(colIndex, e.target.value)}
                    />
                  )}
                </td>
              ))}
              <td className="px-6 py-4 whitespace-nowrap text-center text-sm font-medium">
                <button onClick={saveCreate} className="text-indigo-600 hover:text-indigo-900">
                  Save
                </button>
                <button onClick={cancelCreate} className="text-red-600 hover:text-red-900 ml-4">
                  Cancel
                </button>
              </td>
            </tr>
          )}
        </tbody>
      </table>
      {!isCreating && (
        <button onClick={startCreate} className="mt-4 px-4 py-2 bg-indigo-600 text-white rounded">
          Add New
        </button>
      )}
    </div>
  );
};
